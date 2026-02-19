package dev.lkdcode.kafka.framework.kafka.topic

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaTopicConfig {

    @Bean
    fun tomatoTopic(): NewTopic =
        NewTopic(KafkaTopic.TOMATO, 3, 3.toShort())

    @Bean
    fun tomatoRealtimeDlqTopic(): NewTopic =
        NewTopic(KafkaTopic.TOMATO_REALTIME_DLQ, 3, 3.toShort())

    @Bean
    fun tomatoReplayDlqTopic(): NewTopic =
        NewTopic(KafkaTopic.TOMATO_REPLAY_DLQ, 3, 3.toShort())

    @Bean
    fun tomatoBatchDlqTopic(): NewTopic =
        NewTopic(KafkaTopic.TOMATO_BATCH_DLQ, 3, 3.toShort())
}