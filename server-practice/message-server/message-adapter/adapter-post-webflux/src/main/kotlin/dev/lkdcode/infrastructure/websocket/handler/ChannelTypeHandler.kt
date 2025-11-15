package dev.lkdcode.infrastructure.websocket.handler

import com.fasterxml.jackson.databind.ObjectMapper
import dev.lkdcode.infrastructure.websocket.session.SessionChannels
import dev.lkdcode.infrastructure.websocket.session.SessionSinks
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono


@Service
class ChannelTypeHandler(
    private val objectMapper: ObjectMapper,

    private val webSocketErrorHandler: WebSocketErrorHandler
) {

    fun input(session: WebSocketSession): Mono<Void> {
        return session
            .receive()
            .map(WebSocketMessage::getPayloadAsText)
            .doFinally {
                SessionSinks.remove(session.id)
                SessionChannels.remove(session.id)
            }
            .then()
    }

}