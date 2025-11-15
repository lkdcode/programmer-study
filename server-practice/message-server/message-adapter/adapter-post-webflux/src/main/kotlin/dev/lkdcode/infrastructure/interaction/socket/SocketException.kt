package dev.lkdcode.infrastructure.interaction.socket

class SocketException(
    private val response: SocketMessageCode,
    val payload: Any? = null
) : RuntimeException(response.message) {

    fun name(): String = response.name
    fun code(): String = response.code
    fun message(): String = response.message
    fun getSocketMessageCode(): SocketMessageCode = response
}