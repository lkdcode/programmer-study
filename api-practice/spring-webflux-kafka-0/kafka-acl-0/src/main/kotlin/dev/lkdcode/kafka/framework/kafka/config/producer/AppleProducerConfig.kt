package dev.lkdcode.kafka.framework.kafka.config.producer

import dev.lkdcode.kafka.framework.kafka.KafkaAclUser
import dev.lkdcode.kafka.framework.kafka.config.SaslKafkaProps
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

@Configuration
class AppleProducerConfig(
    private val saslKafkaProps: SaslKafkaProps,
) {

    @Bean
    fun appleProducerTemplate(): ReactiveKafkaProducerTemplate<String, String> {
        val props = saslKafkaProps.producerProps(
            KafkaAclUser.APPLE_PRODUCER,
            KafkaAclUser.APPLE_PRODUCER_PASSWORD,
        )
        return ReactiveKafkaProducerTemplate(SenderOptions.create(props))
    }
}