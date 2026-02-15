package dev.lkdcode.kafka.domains.adapter.output.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.port.output.TomatoProducerPort
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import dev.lkdcode.kafka.framework.kafka.topic.KafkaTopic
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TomatoKafkaAdapter(
    private val reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : TomatoProducerPort {

    override fun send(tomato: TomatoVo): Mono<TomatoDto> =
        reactiveKafkaProducerTemplate
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
