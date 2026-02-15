package dev.lkdcode.kafka.domains.adapter.input.rest

import dev.lkdcode.kafka.domains.adapter.input.rest.request.TomatoRequest
import dev.lkdcode.kafka.domains.adapter.input.rest.response.TomatoResponse
import dev.lkdcode.kafka.domains.application.usecase.SendTomatoUsecase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TomatoApi(
    private val sendTomatoUsecase: SendTomatoUsecase,
) {

    @PostMapping("/tomato")
    fun send(@RequestBody request: TomatoRequest): Mono<TomatoResponse> =
        sendTomatoUsecase
            .send(request.toVO())
            .map { result ->
                TomatoResponse(
                    offset = result.offset,
                    partition = result.partition,
                )
            }
}