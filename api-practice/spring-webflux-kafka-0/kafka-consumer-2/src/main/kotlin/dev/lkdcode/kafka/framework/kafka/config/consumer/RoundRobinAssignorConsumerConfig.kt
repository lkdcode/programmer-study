package dev.lkdcode.kafka.framework.kafka.config.consumer

import dev.lkdcode.kafka.framework.kafka.config.KafkaConsumerProps
import dev.lkdcode.kafka.framework.kafka.consumer.rebalance.RoundRobinRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.RoundRobinAssignor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties

@Configuration
class RoundRobinAssignorConsumerConfig(
    private val kafkaConsumerProps: KafkaConsumerProps,
    private val roundRobinRebalanceListener: RoundRobinRebalanceListener,
) {

    @Bean
    fun roundRobinAssignorKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val props = kafkaConsumerProps.base() + mapOf(
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG to listOf(RoundRobinAssignor::class.java),
        )

        return ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            consumerFactory = DefaultKafkaConsumerFactory(props)
            containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
            containerProperties.setConsumerRebalanceListener(roundRobinRebalanceListener)
        }
    }
}