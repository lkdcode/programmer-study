package lkdcode.transaction.domains.jooq

import io.kotest.matchers.shouldBe
import lkdcode.transaction.config.TestContainersConfig
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestContainersConfig::class)
class JooqDslTest : PidVerificationSpec() {

    @Autowired
    lateinit var jooqDsl: DSLContext

    init {
        Given("Proxy + SPI 모두 갖춘 DSLContext") {
            Then("R2DBC 와 같은 PID → 같은 커넥션을 공유한다") {
                val tuple = transactionalOperator
                    .transactional(r2dbcPid().zipWith(jooqPid(jooqDsl)))
                    .block()!!

                val r2dbcPid = tuple.t1
                val jooqPid = tuple.t2
                println("▶ [Proxy+SPI] R2DBC PID=$r2dbcPid, jOOQ PID=$jooqPid")

                r2dbcPid shouldBe jooqPid
            }
        }
    }
}
