package dev.lkdcode

import dev.lkdcode.cache.service.CacheService
import dev.lkdcode.config.RedisContainerInitializer
import dev.lkdcode.stub.StampedeCacheService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

@SpringBootTest
@ContextConfiguration(initializers = [RedisContainerInitializer::class])
class CacheStampedeIntegrationTest : BehaviorSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var service: StampedeCacheService

    @Autowired
    private lateinit var cacheService: CacheService

    companion object {
        private const val CONCURRENT_COUNT = 5
        private val TIMEOUT = Duration.ofSeconds(15)
    }

    init {
        beforeEach {
            service.resetAll()
            cacheService.deleteByPrefix("StampedeCacheService::").block()
        }

        given("NONE 전략, 캐시 미스 발생") {
            `when`("${CONCURRENT_COUNT}개의 요청이 동시에 도착하면") {
                then("원본 소스를 여러 번 실행하고 캐시 스탬피드가 발생한다") {
                    val requests = (1..CONCURRENT_COUNT).map {
                        service
                            .noneLockFetch("stampede-none")
                            .subscribeOn(Schedulers.parallel())
                    }
                    val results: List<String> = Flux.merge(requests)
                        .collectList()
                        .block(TIMEOUT)!!

                    results.size shouldBe CONCURRENT_COUNT
                    results.all { it == "none-stampede-none" } shouldBe true

                    service.noneCallCount.get() shouldBeGreaterThan 1
                }
            }
        }

        given("SPIN_LOCK 전략, 캐시 미스 발생") {
            `when`("${CONCURRENT_COUNT}개의 요청이 동시에 도착하면") {
                then("원본 소스를 1회만 실행하고 나머지는 폴링 후 캐시 값을 반환한다") {
                    val requests = (1..CONCURRENT_COUNT).map {
                        service
                            .spinLockFetch("stampede-spin")
                            .subscribeOn(Schedulers.parallel())
                    }
                    val results: List<String> = Flux.merge(requests)
                        .collectList()
                        .block(TIMEOUT)!!

                    results.size shouldBe CONCURRENT_COUNT
                    results.all { it == "spin-stampede-spin" } shouldBe true

                    service.spinLockCallCount.get() shouldBe 1
                }
            }
        }

        given("PUB_SUB 전략, 캐시 미스 발생") {
            `when`("${CONCURRENT_COUNT}개의 요청이 동시에 도착하면") {
                then("원본 소스를 1회만 실행하고 나머지는 READY 신호 수신 후 캐시 값을 반환한다") {
                    val requests = (1..CONCURRENT_COUNT).map {
                        service.pubSubFetch("stampede-pubsub")
                            .subscribeOn(Schedulers.parallel())
                    }
                    val results: List<String> = Flux.merge(requests)
                        .collectList()
                        .block(TIMEOUT)!!

                    results.size shouldBe CONCURRENT_COUNT
                    results.all { it == "pubsub-stampede-pubsub" } shouldBe true

                    service.pubSubCallCount.get() shouldBe 1
                }
            }
        }

        given("SOFT_TTL 전략, 캐시 조회") {
            `when`("fresh 상태이면") {
                then("캐시 값을 즉시 반환하고 원본 소스를 호출하지 않는다") {
                    service.softTtlFetch("soft-key").block()
                    service.softTtlCallCount.get() shouldBe 1

                    val freshResult = service.softTtlFetch("soft-key").block()
                    freshResult shouldBe "soft-soft-key"
                    service.softTtlCallCount.get() shouldBe 1
                }
            }

            `when`("stale 상태이면") {
                then("stale 값을 즉시 반환하고 백그라운드에서 원본 소스를 1회 갱신한다") {
                    service.softTtlFetch("soft-stale-key").block()
                    service.softTtlCallCount.get() shouldBe 1

                    Thread.sleep(1_500)

                    val staleResult = service.softTtlFetch("soft-stale-key").block()
                    staleResult shouldNotBe null
                    staleResult shouldBe "soft-soft-stale-key"

                    Thread.sleep(500)
                    service.softTtlCallCount.get() shouldBe 2
                }
            }
        }
    }
}