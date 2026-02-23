package dev.lkdcode.kafka.domains.adapter.output.kafka

import dev.lkdcode.kafka.domains.adapter.output.kafka.mapper.convert
import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.port.output.FireAndForgetTomatoProducerPort
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import dev.lkdcode.kafka.framework.kafka.producer.FireAndForgetKafkaProducer
import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class FireAndForgetTomatoKafkaAdapter(
    private val fireAndForgetKafkaProducer: FireAndForgetKafkaProducer,
) : FireAndForgetTomatoProducerPort {

    override fun send(tomato: TomatoVo): Mono<TomatoDto> =
        fireAndForgetKafkaProducer
            .send(KafkaTopic.TOMATO, tomato.name, tomato)
            .map { it.convert() }
}