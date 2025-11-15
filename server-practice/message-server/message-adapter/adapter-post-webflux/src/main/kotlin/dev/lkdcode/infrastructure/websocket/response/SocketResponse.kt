package dev.lkdcode.infrastructure.websocket.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("success", "code", "message", "payload")
class SocketResponse<T>(
    val success: Boolean = true,
    val code: String? = null,
    val message: String,
    val payload: T? = null,
)