package dev.lkdcode.adapter.input.socket.handler

import dev.lkdcode.adapter.input.socket.strategy.PingStrategy
import dev.lkdcode.adapter.input.socket.strategy.PubStrategy
import dev.lkdcode.adapter.input.socket.strategy.SubStrategy
import dev.lkdcode.adapter.input.socket.strategy.UnSubStrategy
import dev.lkdcode.infrastructure.security.UserAuthentication
import dev.lkdcode.infrastructure.websocket.enum.OperationType
import dev.lkdcode.infrastructure.websocket.request.SocketRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class DirectMessageHandler(
    private val pubStrategy: PubStrategy,
    private val subStrategy: SubStrategy,
    private val unSubStrategy: UnSubStrategy,
    private val pingStrategy: PingStrategy,
) {

    fun handling(
        auth: UserAuthentication,
        sessionId: String,
        socketRequest: SocketRequest,
    ): Mono<Any?> =
        when (socketRequest.operationType) {
            OperationType.PUB -> pubStrategy.execute(auth, sessionId, socketRequest)
            OperationType.SUB -> subStrategy.execute(auth, sessionId, socketRequest)
            OperationType.UN_SUB -> unSubStrategy.execute(auth, sessionId, socketRequest)
            OperationType.PING -> pingStrategy.execute(auth, sessionId, socketRequest)
        }
}