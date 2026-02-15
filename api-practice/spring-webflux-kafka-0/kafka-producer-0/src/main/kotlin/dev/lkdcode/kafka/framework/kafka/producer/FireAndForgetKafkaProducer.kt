package dev.lkdcode.kafka.framework.kafka.producer

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult

@Component
class FireAndForgetKafkaProducer(
    @Qualifier("fireAndForgetProducerTemplate")
    private val fireAndForgetProducerTemplate: ReactiveKafkaProducerTemplate<String, String>,
) {

    fun send(topic: String, key: String, value: String): Mono<SenderResult<Void>> =
        fireAndForgetProducerTemplate.send(topic, key, value)
}