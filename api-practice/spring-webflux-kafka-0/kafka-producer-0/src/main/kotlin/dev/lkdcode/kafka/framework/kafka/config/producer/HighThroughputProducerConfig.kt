package dev.lkdcode.kafka.framework.kafka.config.producer

import dev.lkdcode.kafka.framework.kafka.config.KafkaProducerProps
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

@Configuration
class HighThroughputProducerConfig(
    private val kafkaProducerProps: KafkaProducerProps,
) {

    @Bean
    fun highThroughputProducerTemplate(): ReactiveKafkaProducerTemplate<String, String> {
        val props = kafkaProducerProps.base() + mapOf(
            ProducerConfig.ACKS_CONFIG to "1",
            ProducerConfig.LINGER_MS_CONFIG to 20,
            ProducerConfig.BATCH_SIZE_CONFIG to 65_536,
            ProducerConfig.COMPRESSION_TYPE_CONFIG to "snappy",
            ProducerConfig.BUFFER_MEMORY_CONFIG to 33_554_432L,
        )

        return ReactiveKafkaProducerTemplate(SenderOptions.create(props))
    }
}