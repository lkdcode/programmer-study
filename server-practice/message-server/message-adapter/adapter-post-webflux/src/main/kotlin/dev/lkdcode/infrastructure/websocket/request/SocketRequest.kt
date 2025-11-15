package dev.lkdcode.infrastructure.websocket.request

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.lkdcode.infrastructure.websocket.enum.ChannelType
import dev.lkdcode.infrastructure.websocket.enum.OperationType

data class SocketRequest(
    val operationType: OperationType,
    val channelType: ChannelType,
    val channelId: String,

    val payload: Any,
) {

    @get:JsonIgnore
    val channelKey get() = "${channelType.name}$channelId"
}