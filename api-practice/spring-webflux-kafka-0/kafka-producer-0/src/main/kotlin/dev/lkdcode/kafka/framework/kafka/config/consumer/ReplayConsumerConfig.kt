package dev.lkdcode.kafka.framework.kafka.config.consumer

import com.fasterxml.jackson.databind.ObjectMapper
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
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.util.backoff.FixedBackOff

@Configuration
class ReplayConsumerConfig(
    private val kafkaConsumerProps: KafkaConsumerProps,
    private val objectMapper: ObjectMapper,
    private val dlqKafkaTemplate: KafkaTemplate<String, String>,
) {

    @Bean
    fun replayKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val props = kafkaConsumerProps.base() + mapOf(
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 500,
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to 60_000,
        )

        val recoverer = DeadLetterPublishingRecoverer(dlqKafkaTemplate) { record, _ ->
            TopicPartition(KafkaTopic.TOMATO_REPLAY_DLQ, record.partition())
        }

        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(props)
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        factory.setCommonErrorHandler(DefaultErrorHandler(recoverer, FixedBackOff(3_000L, 3)))
        factory.setRecordMessageConverter(StringJsonMessageConverter(objectMapper))

        return factory
    }
}
