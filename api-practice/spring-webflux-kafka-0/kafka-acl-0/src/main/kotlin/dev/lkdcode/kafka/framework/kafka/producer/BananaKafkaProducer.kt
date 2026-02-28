package dev.lkdcode.kafka.framework.kafka.producer

import dev.lkdcode.kafka.framework.kafka.KafkaAclTopic
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class BananaKafkaProducer(
    private val bananaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>,
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun send(key: String, message: String): Mono<Void> {
        return bananaProducerTemplate.send(KafkaAclTopic.BANANA, key, message)
            .doOnSuccess { log.info("[BANANA_TOPIC] *** SENT     : key={}, value={}", key, message) }
            .doOnError  { log.error("[BANANA_TOPIC] *** SEND FAIL : key={}", key, it) }
            .then()
    }
}