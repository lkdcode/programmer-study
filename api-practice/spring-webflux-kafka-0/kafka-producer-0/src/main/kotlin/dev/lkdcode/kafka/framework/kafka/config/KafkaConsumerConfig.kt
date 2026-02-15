package dev.lkdcode.kafka.framework.kafka.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.CommonErrorHandler
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.util.backoff.FixedBackOff

@Configuration
class KafkaConsumerConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String,
    private val objectMapper: ObjectMapper,
) {

    @Bean(LATEST_KAFKA_LISTENER_CONTAINER_FACTORY)
    fun latestKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()

        factory.consumerFactory = latestConsumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        factory.setCommonErrorHandler(errorHandler())
        factory.setRecordMessageConverter(StringJsonMessageConverter(objectMapper))

        return factory
    }

    @Bean(EARLIEST_KAFKA_LISTENER_CONTAINER_FACTORY)
    fun earliestKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()

        factory.consumerFactory = earliestConsumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        factory.setCommonErrorHandler(errorHandler())
        factory.setRecordMessageConverter(StringJsonMessageConverter(objectMapper))

        return factory
    }

    private fun latestConsumerFactory(): ConsumerFactory<String, String> {
        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to 10000,
            ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG to 3000,
            ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG to 60000,

            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,

            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,

            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to 30000,
            ConsumerConfig.FETCH_MIN_BYTES_CONFIG to 1,
            ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG to 500,

            ConsumerConfig.METADATA_MAX_AGE_CONFIG to 30000,
        )

        return DefaultKafkaConsumerFactory(props)
    }

    private fun earliestConsumerFactory(): ConsumerFactory<String, String> {
        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to 10000,
            ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG to 3000,
            ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG to 60000,

            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,

            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,

            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to 30000,
            ConsumerConfig.FETCH_MIN_BYTES_CONFIG to 1,
            ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG to 500,

            ConsumerConfig.METADATA_MAX_AGE_CONFIG to 30000,
        )

        return DefaultKafkaConsumerFactory(props)
    }

    private fun errorHandler(): CommonErrorHandler {
        val backOff = FixedBackOff(1000L, 3)
        return DefaultErrorHandler(backOff)
    }

    companion object {
        const val LATEST_KAFKA_LISTENER_CONTAINER_FACTORY = "latestKafkaListenerContainerFactory"
        const val EARLIEST_KAFKA_LISTENER_CONTAINER_FACTORY = "earliestKafkaListenerContainerFactory"
    }
}