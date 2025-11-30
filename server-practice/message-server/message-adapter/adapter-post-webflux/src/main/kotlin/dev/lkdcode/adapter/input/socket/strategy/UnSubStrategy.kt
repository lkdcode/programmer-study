package dev.lkdcode.adapter.input.socket.strategy

import dev.lkdcode.adapter.input.socket.handler.DirectMessageStrategy
import dev.lkdcode.infrastructure.security.UserAuthentication
import dev.lkdcode.infrastructure.websocket.request.SocketRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class UnSubStrategy : DirectMessageStrategy {

    override fun execute(
        auth: UserAuthentication,
        sessionId: String,
        socketRequest: SocketRequest
    ): Mono<PubDTO> {
        TODO("Not yet implemented")
    }
}