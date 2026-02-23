package dev.lkdcode.kafka.domains.application.service

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.port.output.AtLeastOnceTomatoProducerPort
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoAtLeastOnceUsecase
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TomatoAtLeastOnceService(
    private val atLeastOnceTomatoProducerPort: AtLeastOnceTomatoProducerPort,
) : SendTomatoAtLeastOnceUsecase {

    override fun send(tomato: TomatoVo): Mono<TomatoDto> =
        atLeastOnceTomatoProducerPort.send(tomato)
}