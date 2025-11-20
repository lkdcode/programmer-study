package dev.lkdcode.infrastructure.kafka


import dev.lkdcode.infrastructure.websocket.SocketBroadcast
import jakarta.annotation.PostConstruct
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import org.springframework.stereotype.Component


@Component
class KafkaMessageConsumer(
    private val consumerTemplate: ReactiveKafkaConsumerTemplate<String, String>,
    private val socketBroadcast: SocketBroadcast
) {

    @PostConstruct
    fun startConsuming() {
        consumerTemplate
            .receiveAutoAck()
            .flatMap { record ->
                val roomId = record.key()
                val message = record.value()
                val topic = record.topic()

                println("KafkaMessageConsumer - Topic[$topic] Room[$roomId]: $message")
                socketBroadcast.toSession(topic + roomId, message)
            }
            .doOnError {
                println("KafkaMessageConsumerERROR $it")
            }
            .subscribe()
    }
}