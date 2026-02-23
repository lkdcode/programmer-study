package dev.lkdcode.kafka.domains.application.service

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.port.output.AtMostOnceTomatoProducerPort
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoAtMostOnceUsecase
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TomatoAtMostOnceService(
    private val atMostOnceTomatoProducerPort: AtMostOnceTomatoProducerPort,
) : SendTomatoAtMostOnceUsecase {

    override fun send(tomato: TomatoVo): Mono<TomatoDto> =
        atMostOnceTomatoProducerPort.send(tomato)
}