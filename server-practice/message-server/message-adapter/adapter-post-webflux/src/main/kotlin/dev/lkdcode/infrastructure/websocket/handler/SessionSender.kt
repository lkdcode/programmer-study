package dev.lkdcode.infrastructure.websocket.handler

import dev.lkdcode.infrastructure.websocket.session.SessionSinks
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono


@Service
class SessionSender {

    fun output(session: WebSocketSession): Mono<Void> =
        session
            .send(
                SessionSinks
                    .asFlux(session.id)
                    .map { session.textMessage(it) }
            )
}