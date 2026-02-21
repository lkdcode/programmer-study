package dev.lkdcode.cache.strategy.refresh

import dev.lkdcode.cache.model.CacheModel
import dev.lkdcode.cache.service.CacheService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

class SoftTtlRefreshHandlerTest : BehaviorSpec({

    given("캐시 조회") {

        `when`("캐시 미스가 발생하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.getValueWithTimestamp(any()) } returns Mono.empty()
            val handler = SoftTtlRefreshHandler(cacheService)

            then("원본 소스에서 값을 가져온다") {
                val missCallCount = AtomicInteger(0)

                StepVerifier.create(
                    handler.readMono(
                        primaryKey = "key",
                        softTtlMillis = 1_000L,
                        onCacheMiss = { missCallCount.incrementAndGet(); Mono.just("source" as Any) },
                        onStale = { Mono.empty() },
                        onError = { Mono.just("error" as Any) },
                    )
                )
                    .expectNext("source")
                    .verifyComplete()

                missCallCount.get() shouldBe 1
            }
        }

        `when`("fresh 상태이면") {
            val freshModel = CacheModel(
                value = "fresh-value" as Any,
                cachedAt = System.currentTimeMillis(),
            )
            val cacheService = mockk<CacheService>()
            every { cacheService.getValueWithTimestamp(any()) } returns Mono.just(freshModel)
            val handler = SoftTtlRefreshHandler(cacheService)

            then("캐시 값을 즉시 반환하고 백그라운드 갱신을 하지 않는다") {
                val staleCallCount = AtomicInteger(0)

                StepVerifier.create(
                    handler.readMono(
                        primaryKey = "key",
                        softTtlMillis = 60_000L,
                        onCacheMiss = { Mono.just("miss" as Any) },
                        onStale = { staleCallCount.incrementAndGet(); Mono.empty() },
                        onError = { Mono.just("error" as Any) },
                    )
                )
                    .expectNext("fresh-value")
                    .verifyComplete()

                staleCallCount.get() shouldBe 0
            }
        }

        `when`("stale 상태이면") {
            val staleModel = CacheModel(
                value = "stale-value" as Any,
                cachedAt = System.currentTimeMillis() - 2_000L,
            )
            val cacheService = mockk<CacheService>()
            every { cacheService.getValueWithTimestamp(any()) } returns Mono.just(staleModel)
            val handler = SoftTtlRefreshHandler(cacheService)

            then("stale 값을 즉시 반환하고 백그라운드 갱신을 트리거한다") {
                val staleCallCount = AtomicInteger(0)

                StepVerifier.create(
                    handler.readMono(
                        primaryKey = "key",
                        softTtlMillis = 1_000L,
                        onCacheMiss = { Mono.just("miss" as Any) },
                        onStale = { staleCallCount.incrementAndGet(); Mono.empty() },
                        onError = { Mono.just("error" as Any) },
                    )
                )
                    .expectNext("stale-value")
                    .verifyComplete()

                staleCallCount.get() shouldBe 1
            }
        }
    }

    given("값을 저장하면") {

        `when`("저장을 요청하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.saveWithTimestamp(any(), any(), any()) } returns Mono.empty()
            val handler = SoftTtlRefreshHandler(cacheService)

            then("타임스탬프와 함께 CacheService에 저장을 위임한다") {
                StepVerifier
                    .create(handler.save("key", "value", Duration.ofMinutes(5)))
                    .verifyComplete()

                verify(exactly = 1) { cacheService.saveWithTimestamp("key", "value", Duration.ofMinutes(5)) }
            }
        }
    }
})