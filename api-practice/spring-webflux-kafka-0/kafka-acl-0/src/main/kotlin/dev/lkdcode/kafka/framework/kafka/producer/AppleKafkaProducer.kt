package dev.lkdcode.kafka.framework.kafka.producer

import dev.lkdcode.kafka.framework.kafka.KafkaAclTopic
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AppleKafkaProducer(
    private val appleProducerTemplate: ReactiveKafkaProducerTemplate<String, String>,
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun send(key: String, message: String): Mono<Void> {
        return appleProducerTemplate.send(KafkaAclTopic.APPLE, key, message)
            .doOnSuccess { log.info("[APPLE_TOPIC] *** SENT     : key={}, value={}", key, message) }
            .doOnError  { log.error("[APPLE_TOPIC] *** SEND FAIL : key={}", key, it) }
            .then()
    }
}