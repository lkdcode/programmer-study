package dev.lkdcode.kafka.domains.adapter.input.rest

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.dto.TomatoDtoList
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoAtLeastOnceUsecase
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoAtMostOnceUsecase
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoTransactionalUsecase
import dev.lkdcode.kafka.domains.domain.model.TomatoColor
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class TomatoDeliverySemanticApi(
    private val sendTomatoTransactionalUsecase: SendTomatoTransactionalUsecase,
    private val sendTomatoAtLeastOnceUsecase: SendTomatoAtLeastOnceUsecase,
    private val sendTomatoAtMostOnceUsecase: SendTomatoAtMostOnceUsecase,
) {

    @PostMapping("/tomato/exactly-once")
    fun sendExactlyOnce(@RequestParam count: Int): Mono<TomatoDtoList> =
        sendTomatoTransactionalUsecase
            .send(buildTomatoList("exactly-once", count))

    @PostMapping("/tomato/at-least-once")
    fun sendAtLeastOnce(@RequestParam count: Int): Flux<TomatoDto> =
        Flux.fromIterable(buildTomatoList("at-least-once", count))
            .flatMap { sendTomatoAtLeastOnceUsecase.send(it) }

    @PostMapping("/tomato/at-most-once")
    fun sendAtMostOnce(@RequestParam count: Int): Flux<TomatoDto> =
        Flux.fromIterable(buildTomatoList("at-most-once", count))
            .flatMap { sendTomatoAtMostOnceUsecase.send(it) }

    private fun buildTomatoList(prefix: String, count: Int): List<TomatoVo> {
        val colors = TomatoColor.entries
        return (0 until count).map { i ->
            TomatoVo(color = colors[i % colors.size], name = "${prefix}_tomato-$i")
        }
    }
}