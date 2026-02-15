package dev.lkdcode.kafka.framework.kafka.producer

import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult

@Component
class ReliableKafkaProducer(
    private val reliableProducerTemplate: ReactiveKafkaProducerTemplate<String, String>,
) {

    fun send(topic: String, key: String, value: String): Mono<SenderResult<Void>> =
        reliableProducerTemplate.send(topic, key, value)
}
