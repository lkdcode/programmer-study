package dev.lkdcode.cache.service

import dev.lkdcode.cache.model.CacheModel
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.UUID

@Component
class ReactiveRedisCacheService(
    private val redisOps: ReactiveRedisOperations<String, Any>,
    private val connectionFactory: ReactiveRedisConnectionFactory
) : CacheService {

    override fun getValue(key: String): Mono<Any> =
        redisOps.opsForValue().get(key)

    override fun save(key: String, value: Any, ttl: Duration): Mono<Void> =
        redisOps.opsForValue().set(key, value, ttl).then()

    override fun getValueWithTimestamp(key: String): Mono<CacheModel> =
        redisOps.opsForValue().get(softTtlKey(key))
            .filter { it is CacheModel }
            .cast(CacheModel::class.java)

    override fun saveWithTimestamp(key: String, value: Any, ttl: Duration): Mono<Void> {
        val cachedValue = CacheModel(value = value)

        return redisOps.opsForValue().set(softTtlKey(key), cachedValue, ttl).then()
    }

    override fun delete(key: String): Mono<Void> =
        Mono.`when`(
            redisOps.opsForValue().delete(key),
            redisOps.opsForValue().delete(softTtlKey(key)),
        )

    override fun deleteByPrefix(prefix: String): Mono<Long> {
        val rawScan = ScanOptions.scanOptions().match("$prefix*").count(1_000).build()
        val softTtlScan = ScanOptions.scanOptions().match("$SOFT_TTL_PREFIX:$prefix*").count(1_000).build()

        return Mono.zip(
            redisOps.unlink(redisOps.scan(rawScan)).defaultIfEmpty(0L),
            redisOps.unlink(redisOps.scan(softTtlScan)).defaultIfEmpty(0L),
        ).map { it.t1 + it.t2 }
    }

    override fun tryLock(key: String, ttl: Duration): Mono<String> {
        val token = UUID.randomUUID().toString()
        return redisOps.opsForValue()
            .setIfAbsent(lockKey(key), token, ttl)
            .flatMap { acquired ->
                if (acquired == true) Mono.just(token)
                else Mono.empty()
            }
    }

    override fun unlock(key: String, lockToken: String): Mono<Boolean> =
        redisOps.execute(UNLOCK_SCRIPT, listOf(lockKey(key)), listOf(lockToken))
            .next()
            .map { it > 0 }
            .defaultIfEmpty(false)

    override fun publishCacheReady(key: String): Mono<Long> =
        redisOps.convertAndSend(cacheReadyChannel(key), CACHE_READY_MESSAGE)

    override fun subscribeCacheReady(key: String, timeout: Duration): Mono<String> {
        val container = ReactiveRedisMessageListenerContainer(connectionFactory)
        val topic = ChannelTopic.of(cacheReadyChannel(key))

        return container.receive(topic)
            .next()
            .map { it.message.toString() }
            .timeout(timeout)
            .doFinally { container.destroyLater().subscribe() }
    }

    private fun lockKey(key: String): String = "$LOCK_PREFIX:$key"
    private fun cacheReadyChannel(key: String): String = "$CHANNEL_PREFIX:$key"
    private fun softTtlKey(key: String): String = "$SOFT_TTL_PREFIX:$key"

    companion object {
        private const val LOCK_PREFIX = "LOCKED"
        private const val CHANNEL_PREFIX = "CACHE:READY"
        private const val CACHE_READY_MESSAGE = "READY"
        private const val SOFT_TTL_PREFIX = "SOFT_TTL"

        private val UNLOCK_SCRIPT = RedisScript.of<Long>(
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end",
            Long::class.java
        )
    }
}