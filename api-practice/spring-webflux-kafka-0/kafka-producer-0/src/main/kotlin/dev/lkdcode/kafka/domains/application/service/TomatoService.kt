package dev.lkdcode.kafka.domains.application.service

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.port.output.FireAndForgetTomatoProducerPort
import dev.lkdcode.kafka.domains.application.port.output.HighThroughputTomatoProducerPort
import dev.lkdcode.kafka.domains.application.port.output.ReliableTomatoProducerPort
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoUsecase
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TomatoService(
    private val reliableTomatoProducerPort: ReliableTomatoProducerPort,
    private val highThroughputTomatoProducerPort: HighThroughputTomatoProducerPort,
    private val fireAndForgetTomatoProducerPort: FireAndForgetTomatoProducerPort,
) : SendTomatoUsecase {

    override fun sendReliable(tomato: TomatoVo): Mono<TomatoDto> =
        reliableTomatoProducerPort.send(tomato)

    override fun sendHighThroughput(tomato: TomatoVo): Mono<TomatoDto> =
        highThroughputTomatoProducerPort.send(tomato)

    override fun sendFireAndForget(tomato: TomatoVo): Mono<TomatoDto> =
        fireAndForgetTomatoProducerPort.send(tomato)
}
