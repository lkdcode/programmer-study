package dev.lkdcode.kafka.domains.adapter.input.event.kafka

import dev.lkdcode.kafka.framework.kafka.consumer.KafkaConsumerFactory
import dev.lkdcode.kafka.framework.kafka.consumer.KafkaConsumerGroup
import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class StickyTomatoListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = [KafkaTopic.TOMATO],
        groupId = KafkaConsumerGroup.TOMATO_STICKY,
        containerFactory = KafkaConsumerFactory.STICKY,
    )
    fun listen(record: ConsumerRecord<String, String>, ack: Acknowledgment) {
        log.info(
            "[STICKY] partition={}, offset={}, value={}",
            record.partition(),
            record.offset(),
            record.value()
        )
        ack.acknowledge()
    }
}