package dev.lkdcode.adapter.input.socket.handler

import dev.lkdcode.infrastructure.websocket.enum.OperationType

enum class DirectMessageH(
    val type: Enum<OperationType>,
    val strategy: DirectMessageStrategy,
) {
    PUB_STRATEGY(OperationType.PUB, PubStrategy()),
    SUB_STRATEGY(OperationType.SUB, PubStrategy()),
    UN_SUB_STRATEGY(OperationType.UN_SUB, PubStrategy()),
    PING_STRATEGY(OperationType.PING, PubStrategy()),
}