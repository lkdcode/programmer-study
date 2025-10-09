package demo.example.server.concurrency

import demo.example.server.base.BaseConcurrencyTest
import demo.example.server.service.ApplicationCASService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.atomic.AtomicInteger

class ApplicationCASServiceTest : BaseConcurrencyTest() {

    @Autowired
    lateinit var applicationCASService: ApplicationCASService

    @Test
    fun `ApplicationCAS 방식 케이스`() {
        val seq = AtomicInteger(0)

        action {
            val userId = seq.incrementAndGet().toLong()
            applicationCASService.apply(userId, EVENT_ID)
        }

        val successCount = eventHistoryJpaRepository.findAllByEventId(EVENT_ID).size
        val stockLeft = eventStockRepository.findById(EVENT_ID).get().stock

        println("✅ 신청자 수: $successCount")
        println("✅ 남은 재고: $stockLeft")

        assertAll(
            { assertThat(TOTAL_USERS).isEqualTo(successCount) },
            { assertThat(stockLeft).isZero() }
        )
    }
}