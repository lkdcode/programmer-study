package dev.lkdcode.stub

import dev.lkdcode.cache.annotation.ReactiveCacheable
import dev.lkdcode.cache.strategy.LockStrategy
import dev.lkdcode.cache.strategy.RefreshStrategy
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

@Service
class StampedeCacheService {

    val noneCallCount = AtomicInteger(0)
    val spinLockCallCount = AtomicInteger(0)
    val pubSubCallCount = AtomicInteger(0)
    val softTtlCallCount = AtomicInteger(0)

    @ReactiveCacheable(
        key = "#key",
        ttl = 30,
        lockStrategy = LockStrategy.NONE,
    )
    fun noneLockFetch(key: String): Mono<String> {
        noneCallCount.incrementAndGet()

        return Mono.delay(Duration.ofMillis(200)).map { "none-$key" }
    }

    @ReactiveCacheable(
        key = "#key",
        ttl = 30,
        lockStrategy = LockStrategy.SPIN_LOCK,
    )
    fun spinLockFetch(key: String): Mono<String> {
        spinLockCallCount.incrementAndGet()

        return Mono.delay(Duration.ofMillis(200)).map { "spin-$key" }
    }

    @ReactiveCacheable(
        key = "#key",
        ttl = 30,
        lockStrategy = LockStrategy.PUB_SUB,
    )
    fun pubSubFetch(key: String): Mono<String> {
        pubSubCallCount.incrementAndGet()

        return Mono.delay(Duration.ofMillis(200)).map { "pubsub-$key" }
    }

    @ReactiveCacheable(
        key = "#key",
        ttl = 10,
        softTtl = 1,
        refreshStrategy = RefreshStrategy.SOFT_TTL,
    )
    fun softTtlFetch(key: String): Mono<String> {
        softTtlCallCount.incrementAndGet()

        return Mono.just("soft-$key")
    }

    fun resetAll() {
        noneCallCount.set(0)
        spinLockCallCount.set(0)
        pubSubCallCount.set(0)
        softTtlCallCount.set(0)
    }
}