package dev.lkdcode.cache.service

import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ScanOptions
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class ReactiveRedisCacheService(
    private val redisOps: ReactiveRedisOperations<String, Any>
) : CacheService {

    override fun getValue(key: String): Mono<Any> =
        redisOps.opsForValue().get(key)

    override fun save(key: String, value: Any, ttl: Duration): Mono<Void> =
        redisOps.opsForValue().set(key, value, ttl).then()

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

    private fun lockKey(key: String): String = "$LOCK_VALUE:$key"

    companion object {
        private const val LOCK_VALUE = "LOCKED"
    }
}