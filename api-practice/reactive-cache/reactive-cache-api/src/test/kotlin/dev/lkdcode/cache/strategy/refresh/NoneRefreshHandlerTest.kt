package dev.lkdcode.cache.strategy.refresh

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

class NoneRefreshHandlerTest : BehaviorSpec({

    given("캐시 조회") {

        `when`("캐시 미스가 발생하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.getValue(any()) } returns Mono.empty()
            val handler = NoneRefreshHandler(cacheService)

            then("원본 소스에서 값을 가져온다") {
                val missCallCount = AtomicInteger(0)

                StepVerifier
                    .create(
                        handler.readMono(
                            primaryKey = "key",
                            softTtlMillis = 0L,
                            onCacheMiss = { missCallCount.incrementAndGet(); Mono.just("from-source" as Any) },
                            onStale = { Mono.empty() },
                            onError = { Mono.just("error" as Any) },
                        )
                    )
                    .expectNext("from-source")
                    .verifyComplete()

                missCallCount.get() shouldBe 1
            }
        }

        `when`("캐시 히트가 발생하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.getValue(any()) } returns Mono.just("cached-value" as Any)
            val handler = NoneRefreshHandler(cacheService)

            then("캐시 값을 반환한다") {
                val missCallCount = AtomicInteger(0)

                StepVerifier
                    .create(
                        handler.readMono(
                            primaryKey = "key",
                            softTtlMillis = 0L,
                            onCacheMiss = { missCallCount.incrementAndGet(); Mono.just("from-source" as Any) },
                            onStale = { Mono.empty() },
                            onError = { Mono.just("error" as Any) },
                        )
                    )
                    .expectNext("cached-value")
                    .verifyComplete()

                missCallCount.get() shouldBe 0
            }
        }
    }

    given("값을 저장하면") {

        `when`("저장을 요청하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.save(any(), any(), any()) } returns Mono.empty()
            val handler = NoneRefreshHandler(cacheService)

            then("CacheService에 저장을 위임하고 완료한다") {
                StepVerifier
                    .create(handler.save("key", "value", Duration.ofMinutes(5)))
                    .verifyComplete()

                verify(exactly = 1) { cacheService.save("key", "value", Duration.ofMinutes(5)) }
            }
        }
    }
})
