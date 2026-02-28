package dev.lkdcode.kafka.framework.kafka.config.consumer

import dev.lkdcode.kafka.framework.kafka.KafkaAclConsumerGroup
import dev.lkdcode.kafka.framework.kafka.KafkaAclUser
import dev.lkdcode.kafka.framework.kafka.config.SaslKafkaProps
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties

@Configuration
class BananaConsumerConfig(
    private val saslKafkaProps: SaslKafkaProps,
) {

    @Bean
    fun bananaKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val props = saslKafkaProps.consumerProps(
            KafkaAclUser.BANANA_CONSUMER,
            KafkaAclUser.BANANA_CONSUMER_PASSWORD,
            KafkaAclConsumerGroup.BANANA,
        )
        return ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            consumerFactory = DefaultKafkaConsumerFactory(props)
            containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
            setAutoStartup(false)
        }
    }
}