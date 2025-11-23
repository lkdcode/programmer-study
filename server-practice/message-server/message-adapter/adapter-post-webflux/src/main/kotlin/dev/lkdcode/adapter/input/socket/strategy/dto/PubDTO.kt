package dev.lkdcode.adapter.input.socket.strategy.dto

import dev.lkdcode.infrastructure.websocket.response.SocketPayload

data class PubDTO(
    val id: Long,
) : SocketPayload