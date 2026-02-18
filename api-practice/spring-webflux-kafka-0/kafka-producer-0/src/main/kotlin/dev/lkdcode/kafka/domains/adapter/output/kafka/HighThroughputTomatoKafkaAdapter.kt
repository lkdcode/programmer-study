package dev.lkdcode.kafka.domains.adapter.output.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.port.output.HighThroughputTomatoProducerPort
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import dev.lkdcode.kafka.framework.kafka.producer.HighThroughputKafkaProducer
import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class HighThroughputTomatoKafkaAdapter(
    private val highThroughputKafkaProducer: HighThroughputKafkaProducer,
    private val objectMapper: ObjectMapper,
) : HighThroughputTomatoProducerPort {

    override fun send(tomato: TomatoVo): Mono<TomatoDto> =
        highThroughputKafkaProducer
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
