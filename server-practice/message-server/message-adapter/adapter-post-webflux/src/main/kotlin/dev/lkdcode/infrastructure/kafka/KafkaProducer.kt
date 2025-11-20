package dev.lkdcode.infrastructure.kafka


import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class KafkaMessageProducer(
    private val producerTemplate: ReactiveKafkaProducerTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {

    fun publish(
        topic: String,
        channelId: String,
        payload: Any
    ): Mono<Void> {
        val json = objectMapper.writeValueAsString(payload)

        return producerTemplate
            .send(topic, channelId, json)
            .doOnNext {
                val md = it.recordMetadata()

                println("TOPIC: ${md.topic()}, OFFSET: ${md.offset()}, PARTITION: ${md.partition()}")
            }
            .then()
    }
}