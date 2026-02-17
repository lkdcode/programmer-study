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
class TomatoBatchListener {

    private val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = [KafkaTopic.TOMATO],
        groupId = KafkaConsumerGroup.TOMATO_BATCH,
        containerFactory = KafkaConsumerFactory.BATCH,
    )
    fun listen(records: List<ConsumerRecord<String, String>>, ack: Acknowledgment) {
        log.info("[BATCH] received {} records", records.size)
        records.forEach { record ->
            log.info(
                "[BATCH] key={}, value={}, partition={}, offset={}",
                record.key(),
                record.value(),
                record.partition(),
                record.offset(),
            )
        }
        ack.acknowledge()
    }
}