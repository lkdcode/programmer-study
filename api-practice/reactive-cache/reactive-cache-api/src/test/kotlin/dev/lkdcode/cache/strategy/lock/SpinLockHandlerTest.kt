package dev.lkdcode.cache.strategy.lock

import dev.lkdcode.cache.service.CacheService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

class SpinLockHandlerTest : BehaviorSpec({

    given("캐시 미스") {

        `when`("락 획득에 성공하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.tryLock(any(), any()) } returns Mono.just("token")
            every { cacheService.unlock(any(), any()) } returns Mono.just(true)
            val handler = SpinLockHandler(cacheService)

            then("원본 소스를 1회 실행하고 결과를 반환한다") {
                val callCount = AtomicInteger(0)

                StepVerifier.create(
                    handler.handleMonoCacheMiss(
                        primaryKey = "key",
                        executeAndSave = { callCount.incrementAndGet(); Mono.just("result" as Any) },
                        readFromCache = { Mono.empty() },
                    )
                )
                    .expectNext("result")
                    .verifyComplete()

                callCount.get() shouldBe 1
            }
        }

        `when`("락 획득에 실패하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.tryLock(any(), any()) } returns Mono.empty()
            val handler = SpinLockHandler(cacheService)

            then("50ms 폴링 재시도 후 캐시 값을 반환한다") {
                val readCallCount = AtomicInteger(0)
                val executeCallCount = AtomicInteger(0)

                StepVerifier.withVirtualTime {
                    handler.handleMonoCacheMiss(
                        primaryKey = "key",
                        executeAndSave = { executeCallCount.incrementAndGet(); Mono.just("executed" as Any) },
                        readFromCache = {
                            if (readCallCount.incrementAndGet() == 1) Mono.empty()
                            else Mono.just("cached" as Any)
                        },
                    )
                }
                    .expectSubscription()
                    .thenAwait(Duration.ofMillis(200))
                    .expectNext("cached")
                    .verifyComplete()

                executeCallCount.get() shouldBe 0
                readCallCount.get() shouldBe 2
            }
        }

        `when`("락 획득에 실패하고 최대 재시도를 초과하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.tryLock(any(), any()) } returns Mono.empty()
            val handler = SpinLockHandler(cacheService)

            then("원본 소스를 직접 실행하고 결과를 반환한다") {
                val executeCallCount = AtomicInteger(0)

                StepVerifier
                    .withVirtualTime {
                        handler.handleMonoCacheMiss(
                            primaryKey = "key",
                            executeAndSave = { executeCallCount.incrementAndGet(); Mono.just("fallback" as Any) },
                            readFromCache = { Mono.empty() },
                        )
                    }
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(2))
                    .expectNext("fallback")
                    .verifyComplete()

                executeCallCount.get() shouldBe 1
            }
        }
    }

    given("백그라운드 갱신 시도") {

        `when`("락 획득에 성공하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.tryLock(any(), any()) } returns Mono.just("token")
            every { cacheService.unlock(any(), any()) } returns Mono.just(true)
            val handler = SpinLockHandler(cacheService)

            then("원본 소스를 1회 실행한다") {
                val callCount = AtomicInteger(0)

                StepVerifier
                    .create(handler.refreshInBackground("key") {
                        callCount.incrementAndGet(); Mono.just("refreshed" as Any)
                    })
                    .verifyComplete()

                callCount.get() shouldBe 1
            }
        }

        `when`("락 획득에 실패하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.tryLock(any(), any()) } returns Mono.empty()
            val handler = SpinLockHandler(cacheService)

            then("원본 소스를 실행하지 않는다") {
                val callCount = AtomicInteger(0)

                StepVerifier
                    .create(handler.refreshInBackground("key") {
                        callCount.incrementAndGet(); Mono.just("refreshed" as Any)
                    })
                    .verifyComplete()

                callCount.get() shouldBe 0
            }
        }
    }
})