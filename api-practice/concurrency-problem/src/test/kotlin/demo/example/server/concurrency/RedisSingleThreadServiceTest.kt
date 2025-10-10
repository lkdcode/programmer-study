package demo.example.server.concurrency

import demo.example.server.base.BaseConcurrencyTest
import demo.example.server.redis.EventQueueWorker
import demo.example.server.service.RedisSingleThreadService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
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
    fun `단일 스레드, 전체 요청이 성공하고 재고 차감 누락은 발생하지 않을 것이다`() {
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
            { assertThat(TOTAL_USERS).isEqualTo(successCount) },
            { assertThat(stockLeft).isEqualTo(BASE_STOCK - successCount) }
        )
    }
}