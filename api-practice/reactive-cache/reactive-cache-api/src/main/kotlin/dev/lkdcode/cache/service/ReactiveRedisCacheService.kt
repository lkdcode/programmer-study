package dev.lkdcode.cache.service

import dev.lkdcode.cache.model.CacheModel
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

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
        redisOps.opsForValue().delete(key).then()

    override fun deleteByPrefix(prefix: String): Mono<Long> {
        val pattern = "$prefix*"
        val scanOptions = ScanOptions.scanOptions().match(pattern).count(1_000).build()
        val keys = redisOps.scan(scanOptions)

        return redisOps.unlink(keys)
    }

    override fun tryLock(key: String, ttl: Duration): Mono<Boolean> =
        redisOps.opsForValue().setIfAbsent(lockKey(key), LOCK_VALUE, ttl)
            .map { it ?: false }

    override fun unlock(key: String): Mono<Void> =
        redisOps.opsForValue().delete(lockKey(key)).then()

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

    private fun lockKey(key: String): String = "$LOCK_VALUE:$key"
    private fun cacheReadyChannel(key: String): String = "$CHANNEL_PREFIX:$key"
    private fun softTtlKey(key: String): String = "$SOFT_TTL_PREFIX:$key"

    companion object {
        private const val LOCK_VALUE = "LOCKED"
        private const val CHANNEL_PREFIX = "CACHE:READY"
        private const val CACHE_READY_MESSAGE = "READY"
        private const val SOFT_TTL_PREFIX = "SOFT_TTL"
    }
}