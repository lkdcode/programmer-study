package dev.lkdcode.kafka.domains.application.service

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.port.output.TomatoProducerPort
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoUsecase
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TomatoService(
    private val tomatoProducerPort: TomatoProducerPort,
) : SendTomatoUsecase {

    override fun send(tomato: TomatoVo): Mono<TomatoDto> =
        tomatoProducerPort.send(tomato)
}
