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
class TomatoReplayListener(
    private val consumeTomatoUsecase: ConsumeTomatoUsecase,
) {

    @KafkaListener(
        topics = [KafkaTopic.TOMATO],
        groupId = KafkaConsumerGroup.TOMATO_REPLAY,
        containerFactory = KafkaConsumerFactory.REPLAY,
    )
    fun listen(record: ConsumerRecord<String, String>, ack: Acknowledgment) {
        consumeTomatoUsecase.consumeSuccess(record.value()).block()
        ack.acknowledge()
    }
}