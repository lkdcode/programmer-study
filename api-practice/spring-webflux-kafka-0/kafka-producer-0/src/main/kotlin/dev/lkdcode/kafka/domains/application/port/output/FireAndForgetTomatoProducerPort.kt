package dev.lkdcode.kafka.domains.application.port.output

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import reactor.core.publisher.Mono

interface FireAndForgetTomatoProducerPort {
    fun send(tomato: TomatoVo): Mono<TomatoDto>
}
