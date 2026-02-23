package dev.lkdcode.kafka.domains.adapter.output.kafka

import dev.lkdcode.kafka.domains.adapter.output.kafka.mapper.convert
import dev.lkdcode.kafka.domains.application.dto.TomatoDtoList
import dev.lkdcode.kafka.domains.application.port.output.TransactionalTomatoProducerPort
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import dev.lkdcode.kafka.framework.kafka.producer.TransactionalKafkaProducer
import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TransactionalTomatoKafkaAdapter(
    private val exactlyOnceKafkaProducer: TransactionalKafkaProducer,
) : TransactionalTomatoProducerPort {

    override fun sendInTransaction(tomatoList: List<TomatoVo>): Mono<TomatoDtoList> =
        exactlyOnceKafkaProducer
            .sendInTransaction(
                records = tomatoList,
                topic = KafkaTopic.TOMATO,
                keySelector = { it.name }

            )
            .map { it.convert() }
}