package demo.example.server.concurrency

import demo.example.server.base.BaseConcurrencyTest
import demo.example.server.service.ApplicationPessimisticLockService
import demo.example.server.service.ApplicationPessimisticLockService2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.atomic.AtomicInteger

class ApplicationPessimisticLockServiceTest : BaseConcurrencyTest() {

    @Autowired
    lateinit var applicationPessimisticLockService: ApplicationPessimisticLockService

    @Autowired
    lateinit var applicationPessimisticLockService2: ApplicationPessimisticLockService2

    @Test
    fun `애플리케이션 비관적 락 실패, 전체 요청이 성공하지만 재고 차감 누락이 발생할 것이다 `() {
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
            { assertThat(TOTAL_USERS).isEqualTo(successCount) },
            { assertThat(stockLeft).isNotEqualTo(BASE_STOCK - successCount) }
        )
    }

    @Test
    fun `애플리케이션 비관적 락, 전체 요청이 성공하고 재고 차감 누락도 발생하지 않을 것이다`() {
        val seq = AtomicInteger(0)

        action {
            val userId = seq.incrementAndGet().toLong()
            applicationPessimisticLockService.applyBySynchronized(userId, EVENT_ID)
        }

        val successCount = eventHistoryJpaRepository.findAllByEventId(EVENT_ID).size
        val stockLeft = eventStockRepository.findById(EVENT_ID).get().stock

        println("✅ 신청자 수: $successCount")
        println("✅ 남은 재고: $stockLeft")

        assertAll(
            { assertThat(TOTAL_USERS).isEqualTo(successCount) },
            { assertThat(stockLeft).isEqualTo(BASE_STOCK - successCount) }
        )
    }
}