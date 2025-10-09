package demo.example.server.concurrency

import demo.example.server.base.BaseConcurrencyTest
import demo.example.server.redis.EventQueueWorker
import demo.example.server.service.RedisSingleThreadService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicInteger

class RedisSingleThreadServiceTest : BaseConcurrencyTest() {

    @Autowired
    lateinit var redisSingleThreadService: RedisSingleThreadService

    @Autowired
    lateinit var worker: EventQueueWorker

    @Autowired
    lateinit var connectionFactory: LettuceConnectionFactory

    @AfterEach
    fun tearDown() {
        worker.stop()
        connectionFactory.destroy()
    }

    @Test
    @Transactional
    fun `단일 스레드를 이용한 케이스`() {
        val seq = AtomicInteger(1)

        action {
            val userId = seq.incrementAndGet().toLong()
            redisSingleThreadService.apply(userId, EVENT_ID)
        }

        worker.waitQueueEmpty()

        val successCount = eventHistoryJpaRepository.findAllByEventId(EVENT_ID).size
        val stockLeft = eventStockRepository.findById(EVENT_ID).get().stock

        println("✅ 신청자 수: $successCount")
        println("✅ 남은 재고: $stockLeft")

        assertAll(
            { Assertions.assertThat(TOTAL_USERS).isEqualTo(successCount) },
            { Assertions.assertThat(stockLeft).isEqualTo(BASE_STOCK - successCount) }
        )
    }
}