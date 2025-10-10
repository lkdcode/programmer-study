package demo.example.server.base

import demo.example.server.container.MySqlContainer
import demo.example.server.container.RedisContainer
import demo.example.server.repository.EventHistoryJpaRepository
import demo.example.server.repository.EventStockJpaRepository
import demo.example.server.support.ExecutorSupport
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(
    initializers = [
        MySqlContainer::class,
        RedisContainer::class,
    ]
)
@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseConcurrencyTest {

    @Autowired
    lateinit var eventStockRepository: EventStockJpaRepository

    @Autowired
    lateinit var eventHistoryJpaRepository: EventHistoryJpaRepository

    val executorSupport: ExecutorSupport = ExecutorSupport()

    @BeforeEach
    fun clear() {
        eventStockRepository
            .findById(EVENT_ID)
            .ifPresent {
                it.stock = BASE_STOCK
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
        const val BASE_STOCK = 100
    }
}