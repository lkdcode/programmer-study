package dev.lkdcode.api

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lkdcode.service.KiwiService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class KiwiWebSocketHandler(
    private val kiwiService: KiwiService,
    private val objectMapper: ObjectMapper,
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> =
        session
            .receive()
            .flatMap { message ->
                val name = message.payloadAsText

                kiwiService
                    .deleteAndFetch(name)
                    .map { objectMapper.writeValueAsString(it) }
                    .flatMap { json ->
                        session.send(Mono.just(session.textMessage(json)))
                    }
            }
            .then()
}