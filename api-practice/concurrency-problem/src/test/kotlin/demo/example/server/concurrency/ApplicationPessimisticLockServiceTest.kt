package demo.example.server.concurrency

import demo.example.server.base.BaseConcurrencyTest
import demo.example.server.service.ApplicationPessimisticLockService
import demo.example.server.service.ApplicationPessimisticLockService2
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicInteger

class ApplicationPessimisticLockServiceTest : BaseConcurrencyTest() {

    @Autowired
    lateinit var applicationPessimisticLockService: ApplicationPessimisticLockService

    @Autowired
    lateinit var applicationPessimisticLockService2: ApplicationPessimisticLockService2

    @Test
    @Transactional
    fun `애플리케이션 비관적 락 실패 케이스`() {
        val seq = AtomicInteger(0)

        action {
            val userId = seq.incrementAndGet().toLong()
            val svc = if (userId % 2L == 0L)
                applicationPessimisticLockService
            else
                applicationPessimisticLockService2

            svc.applyBySynchronized(userId, EVENT_ID)
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