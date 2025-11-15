package dev.lkdcode.infrastructure.interaction.api

import org.springframework.http.HttpStatus

enum class ApiResponseCode(
    val code: String,
    val status: HttpStatus,
    val message: String,
) {

    OK("S001", HttpStatus.OK, "리소스 요청에 성공했습니다."),
    ;
}