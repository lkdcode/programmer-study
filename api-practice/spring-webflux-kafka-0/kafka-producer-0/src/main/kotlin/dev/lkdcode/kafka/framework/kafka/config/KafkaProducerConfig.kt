package dev.lkdcode.kafka.framework.kafka.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

@Configuration
class KafkaProducerConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String,
) {

    private fun baseProps(): Map<String, Any> = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
    )

    @Bean
    @Primary
    fun reliableProducerTemplate(): ReactiveKafkaProducerTemplate<String, String> {
        val props = baseProps() + mapOf(
            ProducerConfig.ACKS_CONFIG to "all",
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to true,
            ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG to 30_000,
            ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG to 10_000,
        )

        return ReactiveKafkaProducerTemplate(SenderOptions.create(props))
    }

    @Bean
    fun highThroughputProducerTemplate(): ReactiveKafkaProducerTemplate<String, String> {
        val props = baseProps() + mapOf(
            ProducerConfig.ACKS_CONFIG to "1",
            ProducerConfig.LINGER_MS_CONFIG to 20,
            ProducerConfig.BATCH_SIZE_CONFIG to 65_536,
            ProducerConfig.COMPRESSION_TYPE_CONFIG to "snappy",
            ProducerConfig.BUFFER_MEMORY_CONFIG to 33_554_432L,
        )

        return ReactiveKafkaProducerTemplate(SenderOptions.create(props))
    }

    @Bean
    fun fireAndForgetProducerTemplate(): ReactiveKafkaProducerTemplate<String, String> {
        val props = baseProps() + mapOf(
            ProducerConfig.ACKS_CONFIG to "0",
            ProducerConfig.RETRIES_CONFIG to 0,
            ProducerConfig.LINGER_MS_CONFIG to 5,
            ProducerConfig.BATCH_SIZE_CONFIG to 32_768,
        )

        return ReactiveKafkaProducerTemplate(SenderOptions.create(props))
    }
}
