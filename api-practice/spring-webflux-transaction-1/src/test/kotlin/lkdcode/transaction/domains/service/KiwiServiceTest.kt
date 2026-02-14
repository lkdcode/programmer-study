package lkdcode.transaction.domains.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.r2dbc.spi.ConnectionFactory
import lkdcode.transaction.config.ConnectionFactorySpy
import lkdcode.transaction.config.SpyConnectionFactoryConfig
import lkdcode.transaction.config.TestContainersConfig
import lkdcode.transaction.domains.model.KiwiR2dbcEntity
import lkdcode.transaction.domains.repository.KiwiR2dbcRepository
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
    lateinit var kiwiNoSPIService: KiwiNoSPIService

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

            When("KiwiService — Proxy + SPI 모두 갖춘 DSLContext") {
                Then("create() 1회: 트랜잭션 커넥션을 공유하므로 새 커넥션을 획득하지 않는다") {
                    kiwiService.deleteByNameThenFindAll("apple").block()

                    println("▶ [Proxy+SPI] acquireCount = ${spy.acquireCount()}")
                    spy.acquireCount() shouldBe 1
                }
            }

            When("KiwiNoBridgeService — Proxy, SPI 모두 없는 DSLContext") {
                Then("create() 2회: jOOQ 가 별도 커넥션을 새로 획득한다") {
                    kiwiNoBridgeService.deleteByNameThenFindAll("apple").block()

                    println("▶ [NoBridge] acquireCount = ${spy.acquireCount()}")
                    spy.acquireCount() shouldBe 2
                }
            }

            When("KiwiNoSPIService — Proxy 만 있고 SPI 없는 DSLContext") {
                Then("create() 2회: Reactor Context 전파 실패로 별도 커넥션을 획득한다") {
                    kiwiNoSPIService.deleteByNameThenFindAll("apple").block()

                    println("▶ [NoSPI] acquireCount = ${spy.acquireCount()}")
                    spy.acquireCount() shouldBe 2
                }
            }
        }
    }
}
