package demo.example.server.concurrency

import demo.example.server.base.BaseConcurrencyTest
import demo.example.server.service.EventWithoutLockService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicInteger

class EventServiceTest : BaseConcurrencyTest() {

    @Autowired
    lateinit var eventWithoutLockService: EventWithoutLockService

    @Test
    @Transactional
    fun `재고 차감 누락 케이스`() {
        val seq = AtomicInteger(1)

        action {
            val userId = seq.incrementAndGet().toLong()
            eventWithoutLockService.apply(userId, EVENT_ID)
        }

        val successCount = eventHistoryJpaRepository.findAllByEventId(EVENT_ID).size
        val stockLeft = eventStockRepository.findById(EVENT_ID).get().stock

        println("✅ 신청자 수: $successCount")
        println("✅ 남은 재고: $stockLeft")

        assertAll(
            { Assertions.assertThat(TOTAL_USERS).isEqualTo(successCount) },
            { Assertions.assertThat(stockLeft).isZero() }
        )
    }
}