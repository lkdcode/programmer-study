package dev.lkdcode.kafka.framework.kafka.config.producer

import dev.lkdcode.kafka.framework.kafka.config.KafkaProducerProps
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

@Configuration
class AtLeastOnceProducerConfig(
    private val kafkaProducerProps: KafkaProducerProps,
) {

    @Bean
    fun atLeastOnceProducerTemplate(): ReactiveKafkaProducerTemplate<String, String> {
        val props = kafkaProducerProps.base() + mapOf(
            ProducerConfig.ACKS_CONFIG to "all",
            ProducerConfig.RETRIES_CONFIG to 3,
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to false,
            ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION to 1,
            ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG to 30_000,
            ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG to 10_000,
        )

        return ReactiveKafkaProducerTemplate(SenderOptions.create(props))
    }
}