package com.sb.framework.api

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("success", "code", "message", "payload")
class ApiResponse<T>(
    val success: Boolean = true,
    val code: String? = null,
    val message: String,
    val payload: T? = null,
) {
    companion object {
        fun <T> of(
            success: Boolean,
            apiCode: ApiResponseCode,
            payload: T? = null,
        ): ApiResponse<T> =
            ApiResponse(
                success = success,
                code = apiCode.code,
                message = apiCode.message,
                payload = payload,
            )

        fun <T> success(
            payload: T,
            code: String = "SUCCESS",
            message: String = "OK",
        ): ApiResponse<T> =
            ApiResponse(
                success = true,
                code = code,
                message = message,
                payload = payload
            )
    }
}

fun <T, R> T.toApiResponse(
    transform: (T) -> R,
    code: String = "SUCCESS",
    message: String = "리소스 요청에 성공했습니다.",
): ApiResponse<R> =
    ApiResponse.success(
        payload = transform(this),
        code = code,
        message = message
    )