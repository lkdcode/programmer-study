package lkdcode.transaction.domains.api

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
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
@Import(TestContainersConfig::class)
class KiwiApiTest : BehaviorSpec() {

    override fun extensions() = listOf(SpringExtension)

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
        beforeEach { seedData() }

        Given("apple 2건, banana 1건이 저장된 상태에서") {

            When("KiwiService — Proxy + ContextAware + Subscriber 모두 갖춘 DSLContext") {
                Then("R2DBC 트랜잭션에 참여하여 미커밋 DELETE 가 반영된 결과를 조회한다") {
                    val result = kiwiService.deleteByNameThenFindAll("apple").block()!!

                    result shouldHaveSize 1
                    result[0].name shouldBe "banana"
                }
            }

            When("KiwiNoBridgeService — 브릿지 없는 DSLContext") {
                Then("R2DBC 트랜잭션에 참여하지 못해 DELETE 이전 데이터를 조회한다") {
                    val result = kiwiNoBridgeService.deleteByNameThenFindAll("apple").block()!!

                    result shouldHaveSize 3
                    result.count { it.name == "apple" } shouldBe 2
                }
            }

            When("KiwiNoSubscriberService — SubscriberProvider 없는 DSLContext") {
                Then("Reactor Context 전파 실패로 R2DBC 트랜잭션에 참여하지 못한다") {
                    val result = kiwiNoSubscriberService.deleteByNameThenFindAll("apple").block()!!

                    result shouldHaveSize 3
                    result.count { it.name == "apple" } shouldBe 2
                }
            }

            When("KiwiNoContextNoSubscriberService — ContextAware·Subscriber 없는 DSLContext") {
                Then("R2DBC 트랜잭션에 참여하지 못한다") {
                    val result = kiwiNoContextNoSubscriberService.deleteByNameThenFindAll("apple").block()!!

                    result shouldHaveSize 3
                    result.count { it.name == "apple" } shouldBe 2
                }
            }
        }
    }
}