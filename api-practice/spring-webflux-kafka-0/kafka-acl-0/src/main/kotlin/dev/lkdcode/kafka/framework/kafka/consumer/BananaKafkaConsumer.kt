package dev.lkdcode.kafka.framework.kafka.consumer

import dev.lkdcode.kafka.framework.kafka.KafkaAclListenerFactory
import dev.lkdcode.kafka.framework.kafka.KafkaAclTopic
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class BananaKafkaConsumer {

    private val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = [KafkaAclTopic.BANANA],
        containerFactory = KafkaAclListenerFactory.BANANA,
    )
    fun consume(record: ConsumerRecord<String, String>, ack: Acknowledgment) {
        log.info("[BANANA_TOPIC] *** RECEIVED : partition={}, offset={}, key={}, value={}",
            record.partition(), record.offset(), record.key(), record.value())
        ack.acknowledge()
    }
}