package dev.lkdcode.adapter.input.socket.handler

import dev.lkdcode.infrastructure.security.UserAuthentication
import dev.lkdcode.infrastructure.websocket.request.SocketRequest
import reactor.core.publisher.Mono


interface DirectMessageStrategy<T> {

    fun execute(
        auth: UserAuthentication,
        sessionId: String,
        socketRequest: SocketRequest,
    ): Mono<T>
}