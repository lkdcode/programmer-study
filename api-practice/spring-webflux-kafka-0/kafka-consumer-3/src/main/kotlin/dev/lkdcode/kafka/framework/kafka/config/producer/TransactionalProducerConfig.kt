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
    fun consumer3TxKafkaSender(): KafkaSender<String, String> {
        val props = kafkaProducerProps.base() + mapOf(
            ProducerConfig.TRANSACTIONAL_ID_CONFIG to "consumer3-tx-producer",
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to true,
            ProducerConfig.ACKS_CONFIG to "all",
        )
        return KafkaSender.create(SenderOptions.create(props))
    }
}