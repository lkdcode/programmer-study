package lkdcode.transaction.domains.jooq

import io.kotest.matchers.shouldNotBe
import lkdcode.transaction.config.TestContainersConfig
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestContainersConfig::class)
class JooqDslNoBridgeTest : PidVerificationSpec() {

    @Autowired
    @Qualifier("jooqDslNoBridge")
    lateinit var jooqDslNoBridge: DSLContext

    init {
        Given("Bridge 없는 DSLContext — TransactionAwareProxy, ContextAware, Subscriber 모두 없음") {
            Then("R2DBC 와 다른 PID → 별도 커넥션을 획득한다") {
                val tuple = transactionalOperator
                    .transactional(r2dbcPid().zipWith(jooqPid(jooqDslNoBridge)))
                    .block()!!

                val r2dbcPid = tuple.t1
                val jooqPid = tuple.t2
                println("▶ [NoBridge] R2DBC PID=$r2dbcPid, jOOQ PID=$jooqPid")

                r2dbcPid shouldNotBe jooqPid
            }
        }
    }
}