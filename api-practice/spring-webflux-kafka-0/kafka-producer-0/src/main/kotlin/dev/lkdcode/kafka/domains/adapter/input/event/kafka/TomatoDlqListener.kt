package dev.lkdcode.kafka.domains.adapter.input.event.kafka

import dev.lkdcode.kafka.framework.kafka.consumer.KafkaConsumerGroup
import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class TomatoDlqListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = [KafkaTopic.TOMATO_REALTIME_DLQ],
        groupId = KafkaConsumerGroup.TOMATO_REALTIME_DLQ,
    )
    fun listenRealtimeDlq(record: ConsumerRecord<String, String>, ack: Acknowledgment) {
        logDlq("REALTIME", record)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = [KafkaTopic.TOMATO_REPLAY_DLQ],
        groupId = KafkaConsumerGroup.TOMATO_REPLAY_DLQ,
    )
    fun listenReplayDlq(record: ConsumerRecord<String, String>, ack: Acknowledgment) {
        logDlq("REPLAY", record)
        ack.acknowledge()
    }

    @KafkaListener(
        topics = [KafkaTopic.TOMATO_BATCH_DLQ],
        groupId = KafkaConsumerGroup.TOMATO_BATCH_DLQ,
    )
    fun listenBatchDlq(record: ConsumerRecord<String, String>, ack: Acknowledgment) {
        logDlq("BATCH", record)
        ack.acknowledge()
    }

    private fun logDlq(type: String, record: ConsumerRecord<String, String>) {
        log.error(
            "[DLQ-{}] key={}, value={}, partition={}, offset={}",
            type,
            record.key(),
            record.value(),
            record.partition(),
            record.offset(),
        )
    }
}