package dev.lkdcode.kafka.framework.kafka.topic

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaTopicConfig {

    @Bean
    fun tomatoTopic(): NewTopic =
        NewTopic(KafkaTopic.TOMATO, 3, 3.toShort())
}
