package dev.lkdcode.kafka.domains.adapter.input.event.kafka

import dev.lkdcode.kafka.domains.application.usecase.ConsumeTomatoUsecase
import dev.lkdcode.kafka.framework.kafka.consumer.KafkaConsumerFactory
import dev.lkdcode.kafka.framework.kafka.consumer.KafkaConsumerGroup
import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class TomatoBatchListener(
    private val consumeTomatoUsecase: ConsumeTomatoUsecase,
) {

    @KafkaListener(
        topics = [KafkaTopic.TOMATO],
        groupId = KafkaConsumerGroup.TOMATO_BATCH,
        containerFactory = KafkaConsumerFactory.BATCH,
    )
    fun listen(records: List<ConsumerRecord<String, String>>, ack: Acknowledgment) {
        consumeTomatoUsecase.consumeSuccessBatch(records.map { it.value() }).block()
        ack.acknowledge()
    }
}