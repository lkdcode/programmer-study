package dev.lkdcode.kafka.framework.kafka.config.consumer

import dev.lkdcode.kafka.framework.kafka.config.KafkaConsumerProps
import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.FixedBackOff

@Configuration
class BatchConsumerConfig(
    private val kafkaConsumerProps: KafkaConsumerProps,
    private val dlqKafkaTemplate: KafkaTemplate<String, String>,
) {

    @Bean
    fun batchKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val props = kafkaConsumerProps.base() + mapOf(
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 1_000,
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to 60_000,
            ConsumerConfig.FETCH_MIN_BYTES_CONFIG to 10_240,
            ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG to 2_000,
        )

        val recoverer = DeadLetterPublishingRecoverer(dlqKafkaTemplate) { record, _ ->
            TopicPartition(KafkaTopic.TOMATO_BATCH_DLQ, record.partition())
        }

        return ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            consumerFactory = DefaultKafkaConsumerFactory(props)
            isBatchListener = true
            setConcurrency(3)
            containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
            setCommonErrorHandler(DefaultErrorHandler(recoverer, FixedBackOff(3_000L, 3)))
        }
    }
}
