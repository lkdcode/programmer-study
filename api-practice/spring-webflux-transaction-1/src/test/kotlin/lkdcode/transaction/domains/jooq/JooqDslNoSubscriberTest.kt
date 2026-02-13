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
class JooqDslNoSubscriberTest : PidVerificationSpec() {

    @Autowired
    @Qualifier("jooqDslNoSubscriber")
    lateinit var jooqDslNoSubscriber: DSLContext

    init {
        Given("SubscriberProvider 없는 DSLContext — TransactionAwareProxy + ContextAware 만 있음") {
            Then("R2DBC 와 다른 PID → Reactor Context 전파 실패로 별도 커넥션을 획득한다") {
                val tuple = transactionalOperator
                    .transactional(r2dbcPid().zipWith(jooqPid(jooqDslNoSubscriber)))
                    .block()!!

                val r2dbcPid = tuple.t1
                val jooqPid = tuple.t2
                println("▶ [NoSubscriber] R2DBC PID=$r2dbcPid, jOOQ PID=$jooqPid")

                r2dbcPid shouldNotBe jooqPid
            }
        }
    }
}