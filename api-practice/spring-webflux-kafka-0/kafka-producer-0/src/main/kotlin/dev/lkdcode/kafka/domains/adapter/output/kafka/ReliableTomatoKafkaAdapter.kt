package dev.lkdcode.kafka.domains.adapter.output.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.port.output.ReliableTomatoProducerPort
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import dev.lkdcode.kafka.framework.kafka.producer.ReliableKafkaProducer
import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ReliableTomatoKafkaAdapter(
    private val reliableKafkaProducer: ReliableKafkaProducer,
    private val objectMapper: ObjectMapper,
) : ReliableTomatoProducerPort {

    override fun send(tomato: TomatoVo): Mono<TomatoDto> =
        reliableKafkaProducer
            .send(
                KafkaTopic.TOMATO,
                tomato.name,
                objectMapper.writeValueAsString(tomato),
            )
            .map { result ->
                TomatoDto(
                    offset = result.recordMetadata().offset(),
                    partition = result.recordMetadata().partition(),
                )
            }
}
