package dev.lkdcode.kafka.domains.adapter.output.kafka

import com.fasterxml.jackson.databind.ObjectMapper
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
    private val objectMapper: ObjectMapper,
) : FireAndForgetTomatoProducerPort {

    override fun send(tomato: TomatoVo): Mono<TomatoDto> =
        fireAndForgetKafkaProducer
            .send(
                KafkaTopic.TOMATO,
                tomato.color.name,
                objectMapper.writeValueAsString(tomato),
            )
            .map { result ->
                TomatoDto(
                    offset = result.recordMetadata().offset(),
                    partition = result.recordMetadata().partition(),
                )
            }
}
