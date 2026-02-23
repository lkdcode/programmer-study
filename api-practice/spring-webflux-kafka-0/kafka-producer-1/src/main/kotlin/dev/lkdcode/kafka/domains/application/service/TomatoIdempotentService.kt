package dev.lkdcode.kafka.domains.application.service

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.port.output.IdempotentTomatoProducerPort
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoIdempotentUsecase
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TomatoIdempotentService(
    private val idempotentTomatoProducerPort: IdempotentTomatoProducerPort,
) : SendTomatoIdempotentUsecase {

    override fun send(tomato: TomatoVo): Mono<TomatoDto> =
        idempotentTomatoProducerPort.send(tomato)
}