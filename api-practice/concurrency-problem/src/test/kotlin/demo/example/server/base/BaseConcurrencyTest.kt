package demo.example.server.base

import demo.example.server.container.MySqlContainers
import demo.example.server.repository.EventHistoryJpaRepository
import demo.example.server.repository.EventStockJpaRepository
import demo.example.server.support.ExecutorSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.transaction.AfterTransaction

@ActiveProfiles("test")
@SpringBootTest
abstract class BaseConcurrencyTest : MySqlContainers() {

    @Autowired
    lateinit var eventStockRepository: EventStockJpaRepository

    @Autowired
    lateinit var eventHistoryJpaRepository: EventHistoryJpaRepository

    val executorSupport: ExecutorSupport = ExecutorSupport()

    @AfterTransaction
    fun clearAndInit() {
        eventStockRepository
            .findById(EVENT_ID)
            .ifPresent {
                it.stock = 100
                eventStockRepository.save(it)
            }
        eventHistoryJpaRepository.deleteAll()
    }

    fun action(action: () -> Unit) {
        executorSupport.execute(concurrency = TOTAL_USERS) {
            action()
        }
    }

    companion object {
        const val EVENT_ID: Long = 1L
        const val TOTAL_USERS = 100
    }
}