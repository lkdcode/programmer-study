package com.sb.framework.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

typealias ApiEntity<T> = ResponseEntity<ApiResponse<T>>
typealias ApiMono<T> = Mono<ResponseEntity<ApiResponse<T>>>

fun <T> T.toApiResponseEntity(
    status: HttpStatus = HttpStatus.OK,
    message: String = "OK",
    code: String? = null,
): ApiEntity<T> =
    ResponseEntity.status(status).body(
        ApiResponse(
            success = true,
            code = code,
            message = message,
            payload = this
        )
    )

fun <T> Mono<T>.toApiResponseEntity(
    status: HttpStatus = HttpStatus.OK,
    message: String = "OK",
    code: String? = null,
): ApiMono<T> =
    this.map { payload ->
        ResponseEntity.status(status).body(
            ApiResponse(
                success = true,
                code = code,
                message = message,
                payload = payload
            )
        )
    }