package com.sb.framework.redis

import com.sb.framework.cache.CacheService
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration


@Service
class ReactiveRedisCacheService(
    private val redisOps: ReactiveRedisOperations<String, Any>
) : CacheService {

    override fun getValue(key: String): Mono<Any> =
        redisOps.opsForValue().get(key)

    override fun save(key: String, value: Any, ttl: Duration): Mono<Void> =
        redisOps.opsForValue().set(key, value, ttl).then()

    override fun delete(key: String): Mono<Void> =
        redisOps.opsForValue().delete(key).then()
}