package dev.lkdcode.infrastructure.interaction.socket

enum class SocketMessageCode(
    val code: String,
    val message: String,
) {
    OK("WS001", "리소스 요청에 성공했습니다."),

    ;
}