package dev.lkdcode.adapter.input.socket.handler

import dev.lkdcode.infrastructure.security.UserAuthentication
import dev.lkdcode.infrastructure.websocket.enum.OperationType
import dev.lkdcode.infrastructure.websocket.request.SocketRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class DirectMessageHandler(
    private val list: List<DirectMessageStrategy>
) {

    fun <T> handling(
        auth: UserAuthentication,
        sessionId: String,
        socketRequest: SocketRequest,
    ): Mono<T> =
        when (socketRequest.operationType) {
            OperationType.PUB -> TODO()
            OperationType.SUB -> TODO()
            OperationType.UN_SUB -> TODO()
            OperationType.PING -> TODO()
        }
}
