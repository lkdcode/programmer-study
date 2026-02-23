package dev.lkdcode.kafka.framework.kafka.config.producer

import dev.lkdcode.kafka.framework.kafka.config.KafkaProducerProps
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

@Configuration
class IdempotentProducerConfig(
    private val kafkaProducerProps: KafkaProducerProps,
) {

    @Bean
    fun idempotentProducerTemplate(): ReactiveKafkaProducerTemplate<String, String> {
        val props = kafkaProducerProps.base() + mapOf(
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to true,
            ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION to 5,
            ProducerConfig.RETRIES_CONFIG to 5,
            ProducerConfig.ACKS_CONFIG to "all",
        )

        return ReactiveKafkaProducerTemplate(SenderOptions.create(props))
    }
}