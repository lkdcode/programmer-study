package dev.lkdcode.kafka.framework.kafka.config.producer

import dev.lkdcode.kafka.framework.kafka.config.KafkaProducerProps
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

@Configuration
class FireAndForgetProducerConfig(
    private val kafkaProducerProps: KafkaProducerProps,
) {

    @Bean
    fun fireAndForgetProducerTemplate(): ReactiveKafkaProducerTemplate<String, String> {
        val props = kafkaProducerProps.base() + mapOf(
            ProducerConfig.ACKS_CONFIG to "0",
            ProducerConfig.RETRIES_CONFIG to 0,
            ProducerConfig.LINGER_MS_CONFIG to 5,
            ProducerConfig.BATCH_SIZE_CONFIG to 32_768,
        )

        return ReactiveKafkaProducerTemplate(SenderOptions.create(props))
    }
}