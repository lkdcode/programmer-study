package dev.lkdcode.kafka.framework.kafka.config.consumer

import dev.lkdcode.kafka.framework.kafka.config.KafkaConsumerProps
import dev.lkdcode.kafka.framework.kafka.consumer.NormalGroupRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties

@Configuration
class NormalGroupConsumerConfig(
    private val kafkaConsumerProps: KafkaConsumerProps,
    private val normalGroupRebalanceListener: NormalGroupRebalanceListener,
) {

    @Bean
    fun normalGroupKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val props = kafkaConsumerProps.base() + mapOf(
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to 10_000,
        )

        return ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            consumerFactory = DefaultKafkaConsumerFactory(props)
            containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
            containerProperties.setConsumerRebalanceListener(normalGroupRebalanceListener)
        }
    }
}