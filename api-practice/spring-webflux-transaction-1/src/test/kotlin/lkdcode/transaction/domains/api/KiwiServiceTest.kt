package lkdcode.transaction.domains.api

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.r2dbc.spi.ConnectionFactory
import lkdcode.transaction.config.ConnectionFactorySpy
import lkdcode.transaction.config.SpyConnectionFactoryConfig
import lkdcode.transaction.config.TestContainersConfig
import lkdcode.transaction.domains.model.KiwiR2dbcEntity
import lkdcode.transaction.domains.repository.KiwiR2dbcRepository
import lkdcode.transaction.domains.service.KiwiNoBridgeService
import lkdcode.transaction.domains.service.KiwiNoContextNoSubscriberService
import lkdcode.transaction.domains.service.KiwiNoSubscriberService
import lkdcode.transaction.domains.service.KiwiService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import reactor.core.publisher.Flux

@SpringBootTest
@Import(TestContainersConfig::class, SpyConnectionFactoryConfig::class)
class KiwiServiceTest : BehaviorSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var connectionFactory: ConnectionFactory

    @Autowired
    lateinit var kiwiR2dbcRepository: KiwiR2dbcRepository

    @Autowired
    lateinit var kiwiService: KiwiService

    @Autowired
    lateinit var kiwiNoBridgeService: KiwiNoBridgeService

    @Autowired
    lateinit var kiwiNoSubscriberService: KiwiNoSubscriberService

    @Autowired
    lateinit var kiwiNoContextNoSubscriberService: KiwiNoContextNoSubscriberService

    private val spy: ConnectionFactorySpy
        get() = connectionFactory as ConnectionFactorySpy

    private fun seedData() {
        kiwiR2dbcRepository.deleteAll()
            .thenMany(
                Flux.just("apple", "banana", "apple")
                    .concatMap { kiwiR2dbcRepository.save(KiwiR2dbcEntity(name = it)) }
            )
            .collectList()
            .block()
    }

    init {
        beforeEach {
            seedData()
            spy.reset()
        }

        Given("Service 실행 중 ConnectionFactory.create() 호출 횟수로 커넥션 공유 여부를 확인하면") {

            When("KiwiService — Bridge 가 있는 DSLContext") {
                Then("create() 1회: 트랜잭션 커넥션을 공유하므로 새 커넥션을 획득하지 않는다") {
                    kiwiService.deleteByNameThenFindAll("apple").block()

                    println("▶ [Bridge O] acquireCount = ${spy.acquireCount()}")
                    spy.acquireCount() shouldBe 1
                }
            }

            When("KiwiNoBridgeService — Bridge 가 없는 DSLContext") {
                Then("create() 2회: jOOQ 가 별도 커넥션을 새로 획득한다") {
                    kiwiNoBridgeService.deleteByNameThenFindAll("apple").block()

                    println("▶ [Bridge X] acquireCount = ${spy.acquireCount()}")
                    spy.acquireCount() shouldBe 2
                }
            }

            When("KiwiNoSubscriberService — Subscriber 없는 DSLContext") {
                Then("create() 2회: Reactor Context 전파 실패로 별도 커넥션을 획득한다") {
                    kiwiNoSubscriberService.deleteByNameThenFindAll("apple").block()

                    println("▶ [NoSubscriber] acquireCount = ${spy.acquireCount()}")
                    spy.acquireCount() shouldBe 2
                }
            }

            When("KiwiNoContextNoSubscriberService — ContextAware·Subscriber 없는 DSLContext") {
                Then("create() 2회: 별도 커넥션을 획득한다") {
                    kiwiNoContextNoSubscriberService.deleteByNameThenFindAll("apple").block()

                    println("▶ [NoCtx+NoSub] acquireCount = ${spy.acquireCount()}")
                    spy.acquireCount() shouldBe 2
                }
            }
        }
    }
}
