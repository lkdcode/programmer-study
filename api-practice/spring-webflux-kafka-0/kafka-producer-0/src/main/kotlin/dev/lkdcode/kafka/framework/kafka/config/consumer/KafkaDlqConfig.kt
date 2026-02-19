package dev.lkdcode.kafka.framework.kafka.config.consumer

import dev.lkdcode.kafka.framework.kafka.config.KafkaProducerProps
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate

@Configuration
class KafkaDlqConfig(
    private val kafkaProducerProps: KafkaProducerProps,
) {

    @Bean
    fun dlqKafkaTemplate(): KafkaTemplate<String, String> =
        KafkaTemplate(DefaultKafkaProducerFactory(kafkaProducerProps.base()))
}
