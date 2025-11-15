package dev.lkdcode.infrastructure.websocket

import dev.lkdcode.infrastructure.websocket.handler.ChannelTypeHandler
import dev.lkdcode.infrastructure.websocket.handler.SessionSender
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono


@Component
class SocketHandler(
    private val broadcast: SocketBroadcast,

    private val receiver: ChannelTypeHandler,
    private val sender: SessionSender,
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        broadcast.init(session.id)

        return Mono
            .zip(receiver.input(session), sender.output(session))
            .then()
    }
}