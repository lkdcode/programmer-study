package dev.lkdcode.cache.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.ReactiveRedisOperations

@Configuration
class RedisWarmupConfig(
    private val reactiveRedisOperations: ReactiveRedisOperations<String, Any>,
) {

    @PostConstruct
    fun warmup() {
        reactiveRedisOperations
            .opsForValue()
            .get("warmup")
            .block()
    }
}