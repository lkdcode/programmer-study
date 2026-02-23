package dev.lkdcode.kafka.framework.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult

@Component
class HighThroughputKafkaProducer(
    @Qualifier("highThroughputProducerTemplate")
    private val highThroughputProducerTemplate: ReactiveKafkaProducerTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {

    fun send(topic: String, key: String, value: Any): Mono<SenderResult<Void>> =
        highThroughputProducerTemplate.send(topic, key, objectMapper.writeValueAsString(value))
}