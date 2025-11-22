package dev.lkdcode.adapter.input.socket.handler

import dev.lkdcode.infrastructure.security.UserAuthentication
import dev.lkdcode.infrastructure.websocket.request.SocketRequest
import reactor.core.publisher.Mono

class PubStrategy: DirectMessageStrategy {

    override fun <T> execute(
        auth: UserAuthentication,
        sessionId: String,
        socketRequest: SocketRequest
    ): Mono<T> {
        TODO("Not yet implemented")
    }
}