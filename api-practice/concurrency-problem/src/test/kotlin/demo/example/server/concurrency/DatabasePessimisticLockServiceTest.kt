package demo.example.server.concurrency

import demo.example.server.base.BaseConcurrencyTest
import demo.example.server.service.ApplicationPessimisticLockService
import demo.example.server.service.ApplicationPessimisticLockService2
import demo.example.server.service.DatabasePessimisticLockService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicInteger

class DatabasePessimisticLockServiceTest  : BaseConcurrencyTest() {

    @Autowired
    lateinit var databasePessimisticLockService: DatabasePessimisticLockService

    @Test
    @Transactional
    fun `데이터베이스 비관적 락 케이스`() {
        val seq = AtomicInteger(0)

        action {
            val userId = seq.incrementAndGet().toLong()
            databasePessimisticLockService.applyForUpdate(userId, EVENT_ID)
        }

        val successCount = eventHistoryJpaRepository.findAllByEventId(EVENT_ID).size
        val stockLeft = eventStockRepository.findById(EVENT_ID).get().stock

        println("✅ 신청자 수: $successCount")
        println("✅ 남은 재고: $stockLeft")

        assertAll(
            { Assertions.assertThat(TOTAL_USERS).isEqualTo(successCount) },
            { Assertions.assertThat(0).isEqualTo(stockLeft) }
        )
    }
}