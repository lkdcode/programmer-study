package dev.lkdcode.kafka.domains.adapter.input.rest

import dev.lkdcode.kafka.domains.adapter.input.rest.response.TomatoResponse
import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoUsecase
import dev.lkdcode.kafka.domains.domain.model.TomatoColor
import dev.lkdcode.kafka.domains.domain.model.TomatoVo
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class TomatoApi(
    private val sendTomatoUsecase: SendTomatoUsecase,
) {

    @PostMapping("/tomato/reliable")
    fun sendReliable(@RequestParam count: Int): Flux<TomatoResponse> =
        sendBulk(count) { sendTomatoUsecase.sendReliable(it) }

    @PostMapping("/tomato/high-throughput")
    fun sendHighThroughput(@RequestParam count: Int): Flux<TomatoResponse> =
        sendBulk(count) { sendTomatoUsecase.sendHighThroughput(it) }

    @PostMapping("/tomato/fire-and-forget")
    fun sendFireAndForget(@RequestParam count: Int): Flux<TomatoResponse> =
        sendBulk(count) { sendTomatoUsecase.sendFireAndForget(it) }

    private fun sendBulk(
        count: Int,
        sender: (TomatoVo) -> Mono<TomatoDto>,
    ): Flux<TomatoResponse> {
        val colors = TomatoColor.entries
        return Flux
            .range(0, count)
            .flatMap { i ->
                sender(TomatoVo(color = colors[i % colors.size], name = "tomato-$i"))
                    .map { TomatoResponse(offset = it.offset, partition = it.partition) }
            }
    }
}
