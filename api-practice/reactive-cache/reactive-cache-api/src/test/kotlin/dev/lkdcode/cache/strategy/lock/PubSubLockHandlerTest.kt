package dev.lkdcode.cache.strategy.lock

import dev.lkdcode.cache.service.CacheService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.concurrent.atomic.AtomicInteger

class PubSubLockHandlerTest : BehaviorSpec({
    given("캐시 미스") {

        `when`("락 획득에 성공하면") {
            val cacheService = mockk<CacheService>()

            every { cacheService.subscribeCacheReady(any(), any()) } returns Mono.never()
            every { cacheService.tryLock(any(), any()) } returns Mono.just("token")
            every { cacheService.publishCacheReady(any()) } returns Mono.just(1L)
            every { cacheService.unlock(any(), any()) } returns Mono.just(true)

            val handler = PubSubLockHandler(cacheService)

            then("원본 소스를 1회 실행하고 결과를 반환한다") {
                val callCount = AtomicInteger(0)

                StepVerifier
                    .create(
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

            every { cacheService.subscribeCacheReady(any(), any()) } returns Mono.just("READY")
            every { cacheService.tryLock(any(), any()) } returns Mono.empty()
            val handler = PubSubLockHandler(cacheService)

            then("READY 신호 수신 후 캐시 값을 반환한다") {
                val readCallCount = AtomicInteger(0)
                val executeCallCount = AtomicInteger(0)

                StepVerifier
                    .create(
                        handler.handleMonoCacheMiss(
                            primaryKey = "key",
                            executeAndSave = { executeCallCount.incrementAndGet(); Mono.just("executed" as Any) },
                            readFromCache = { readCallCount.incrementAndGet(); Mono.just("cached" as Any) },
                        )
                    )
                    .expectNext("cached")
                    .verifyComplete()

                readCallCount.get() shouldBe 1
                executeCallCount.get() shouldBe 0
            }
        }

        `when`("락 획득에 실패하고 Pub/Sub 수신 후에도 캐시미스가 발생하면") {
            val cacheService = mockk<CacheService>()
            every { cacheService.subscribeCacheReady(any(), any()) } returns Mono.just("READY")
            every { cacheService.tryLock(any(), any()) } returns Mono.empty()
            val handler = PubSubLockHandler(cacheService)

            then("원본 소스를 직접 실행하고 결과를 반환한다") {
                val executeCallCount = AtomicInteger(0)

                StepVerifier
                    .create(
                        handler.handleMonoCacheMiss(
                            primaryKey = "key",
                            executeAndSave = { executeCallCount.incrementAndGet(); Mono.just("fallback" as Any) },
                            readFromCache = { Mono.empty() },
                        )
                    )
                    .expectNext("fallback")
                    .verifyComplete()

                executeCallCount.get() shouldBe 1
            }
        }
    }
})