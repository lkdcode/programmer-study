package dev.lkdcode.adapter.input.socket.strategy

import dev.lkdcode.adapter.input.socket.handler.DirectMessageStrategy
import dev.lkdcode.application.ports.output.guard.DirectMessageGuard
import dev.lkdcode.infrastructure.security.UserAuthentication
import dev.lkdcode.infrastructure.websocket.request.SocketRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class SubStrategy(
    private val directMessageGuard: DirectMessageGuard,
) : DirectMessageStrategy<Void> {

    override fun execute(
        auth: UserAuthentication,
        sessionId: String,
        socketRequest: SocketRequest
    ): Mono<Void> = Mono
        .fromCallable { directMessageGuard.requireMemberOfMessageRoom() }
        .then()
}