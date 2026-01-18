package com.sb.framework.api

import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux

typealias FluxApiEntity<T> = Flux<ResponseEntity<ApiResponse<T>>>

typealias ApiResponseEntity<T> = ResponseEntity<ApiResponse<T>>

fun <T> success(
    payload: T? = null
): ApiResponseEntity<T> =
    ResponseEntity
        .status(ApiResponseCode.OK.status)
        .body(
            ApiResponse(
                success = true,
                code = ApiResponseCode.OK.message,
                message = ApiResponseCode.OK.code,
                payload = payload
            )
        )

fun <T> created(
    payload: T? = null
): ApiResponseEntity<T> =
    ResponseEntity
        .status(ApiResponseCode.CREATED.status)
        .body(
            ApiResponse(
                success = true,
                code = ApiResponseCode.CREATED.message,
                message = ApiResponseCode.CREATED.code,
                payload = payload
            )
        )

fun <T> updated(
    payload: T? = null
): ApiResponseEntity<T> =
    ResponseEntity
        .status(ApiResponseCode.UPDATED.status)
        .body(
            ApiResponse(
                success = true,
                code = ApiResponseCode.UPDATED.message,
                message = ApiResponseCode.UPDATED.code,
                payload = null
            )
        )

fun <T> deleted(
    payload: T? = null
): ApiResponseEntity<T> =
    ResponseEntity
        .status(ApiResponseCode.DELETED.status)
        .body(
            ApiResponse(
                success = true,
                code = ApiResponseCode.DELETED.message,
                message = ApiResponseCode.DELETED.code,
                payload = payload
            )
        )

fun <T> clientError(
    apiResponseCode: ApiResponseCode,
    payload: T? = null,
): ApiResponseEntity<T> =
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

fun <T> serverError(
    apiResponseCode: ApiResponseCode,
    payload: T? = null,
): ApiResponseEntity<T> =
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