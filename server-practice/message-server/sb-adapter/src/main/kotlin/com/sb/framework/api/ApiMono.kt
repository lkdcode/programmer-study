package com.sb.framework.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono

typealias MonoApiEntity<T> = ResponseEntity<ApiResponse<T>>
typealias ApiMono<T> = Mono<ResponseEntity<ApiResponse<T>>>

fun <T> clientErrorEntity(
    apiResponseCode: ApiResponseCode,
    payload: T? = null,
): MonoApiEntity<T> =
    ResponseEntity
        .status(apiResponseCode.status)
        .body(
            ApiResponse(
                success = false,
                code = apiResponseCode.code,
                message = apiResponseCode.message,
                payload = payload,
            )
        )

fun <T> serverErrorEntity(
    apiResponseCode: ApiResponseCode,
    payload: T? = null,
): MonoApiEntity<T> =
    ResponseEntity
        .status(apiResponseCode.status)
        .body(
            ApiResponse(
                success = false,
                code = apiResponseCode.code,
                message = apiResponseCode.message,
                payload = payload,
            )
        )

fun <T> clientError(
    apiResponseCode: ApiResponseCode,
    payload: T? = null,
): ApiMono<T> = Mono.just(clientErrorEntity(apiResponseCode, payload))

fun <T> serverError(
    apiResponseCode: ApiResponseCode,
    payload: T? = null,
): ApiMono<T> = Mono.just(serverErrorEntity(apiResponseCode, payload))

fun <T> T.toApiResponseEntity(
    status: HttpStatus = HttpStatus.OK,
    message: String = "OK",
    code: String? = null,
): MonoApiEntity<T> =
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
): ApiMono<T> = this
    .map { payload ->
        ResponseEntity.status(status).body(
            ApiResponse(
                success = true,
                code = code,
                message = message,
                payload = payload
            )
        )
    }