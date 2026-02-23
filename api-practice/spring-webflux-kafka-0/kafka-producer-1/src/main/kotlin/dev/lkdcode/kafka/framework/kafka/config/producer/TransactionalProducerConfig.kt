package dev.lkdcode.kafka.framework.kafka.config.producer

import dev.lkdcode.kafka.framework.kafka.config.KafkaProducerProps
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions

@Configuration
class TransactionalProducerConfig(
    private val kafkaProducerProps: KafkaProducerProps,
) {

    @Bean
    fun exactlyOnceKafkaSender(): KafkaSender<String, String> {
        val props = kafkaProducerProps.base() + mapOf(
            ProducerConfig.TRANSACTIONAL_ID_CONFIG to "tomato-tx-producer",
            ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG to 30_000,
            ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG to 10_000,
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to true,
            ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION to 5,
            ProducerConfig.RETRIES_CONFIG to 5,
            ProducerConfig.ACKS_CONFIG to "all",
        )

        return KafkaSender.create(SenderOptions.create(props))
    }
}