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
        // block()은 reactive 스레드(netty event loop)에서 호출 시 IllegalStateException을 유발한다.
        // warmup은 연결 확인 목적이므로 결과를 동기적으로 기다릴 필요가 없다.
        reactiveRedisOperations
            .opsForValue()
            .get("warmup")
            .subscribe()
    }
}