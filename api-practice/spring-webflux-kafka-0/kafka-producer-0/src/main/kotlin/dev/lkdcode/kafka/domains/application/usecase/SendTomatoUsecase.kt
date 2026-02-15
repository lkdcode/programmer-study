package dev.lkdcode.kafka.domains.application.usecase

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import reactor.core.publisher.Mono

interface SendTomatoUsecase {
    fun send(tomato: TomatoVo): Mono<TomatoDto>
}
