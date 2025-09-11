package lkdcode.server.debezium.listener

import io.debezium.engine.ChangeEvent
import io.debezium.engine.DebeziumEngine
import io.debezium.engine.format.Json
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.Executors


@Service
class DebeziumListener(
    @Qualifier("debeziumConnector")
    private val properties: Properties
) {
    private val executor = Executors.newSingleThreadExecutor { r ->
        Thread(r, "debezium-lkdcode").apply { isDaemon = false }
    }

    private lateinit var engine: DebeziumEngine<ChangeEvent<String, String>>

    @PostConstruct
    fun start() {
        engine = DebeziumEngine.create(Json::class.java)
            .using(properties)
            .notifying { records: List<ChangeEvent<String, String>>,
                         committer: DebeziumEngine.RecordCommitter<ChangeEvent<String, String>> ->
                records.forEach { record ->
                    try {
                        println("CDC destination: ${record.destination()}")
                        println("CDC key: ${record.key()}")
                        println("CDC value: ${record.value()}")

                        committer.markProcessed(record)
                    } catch (e: Exception) {
                        println("ERROR: $e")
                    }
                }

                committer.markBatchFinished()
            }
            .build()

        executor.submit(engine)
    }

    @PreDestroy
    fun stop() {
        try {
            if (this::engine.isInitialized) engine.close()
        } finally {
            executor.shutdown()
        }
    }
}