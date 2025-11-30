package dev.lkdcode.api.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono


@JsonPropertyOrder("success", "code", "message", "payload")
class ApiResponse<T> private constructor(
    val success: Boolean,
    val code: String,
    val message: String,
    val payload: T? = null,
) {
    companion object {

        fun <T> ofDTO(
            success: Boolean,
            apiCode: ApiResponseCode,
            payload: T? = null
        ): ApiResponse<T> = ApiResponse(success, apiCode.code, apiCode.message, payload)

        fun <T> success(
            apiCode: ApiResponseCode = ApiResponseCode.OK,
            payload: T? = null
        ): Mono<ResponseEntity<ApiResponse<T?>>> = Mono.just(
            ResponseEntity
                .status(apiCode.status)
                .body(ApiResponse(true, apiCode.code, apiCode.message, payload))
        )

        fun <T> successCreated(payload: T? = null): Mono<ResponseEntity<ApiResponse<T?>>> = Mono.just(
            ResponseEntity
                .status(ApiResponseCode.CREATED.status)
                .body(
                    ApiResponse(true, ApiResponseCode.CREATED.code, ApiResponseCode.CREATED.message, payload)
                )
        )

        fun <T> successUpdated(
            payload: T? = null
        ): Mono<ResponseEntity<ApiResponse<T?>>> = Mono.just(
            ResponseEntity
                .status(ApiResponseCode.UPDATED.status)
                .body(
                    ApiResponse(true, ApiResponseCode.UPDATED.code, ApiResponseCode.UPDATED.message, payload)
                )
        )

        fun <T> successDeleted(
            payload: T? = null
        ): Mono<ResponseEntity<ApiResponse<T?>>> = Mono.just(
            ResponseEntity
                .status(ApiResponseCode.DELETED.status)
                .body(ApiResponse(true, ApiResponseCode.DELETED.code, ApiResponseCode.DELETED.message, payload))
        )

        fun <T> clientError(
            apiResponseCode: ApiResponseCode = ApiResponseCode.REQUEST_INVALID,
            payload: T? = null
        ): Mono<ResponseEntity<ApiResponse<T>>> = Mono.just(
            ResponseEntity
                .status(apiResponseCode.status)
                .body(ApiResponse(false, apiResponseCode.code, apiResponseCode.message, payload))
        )

        fun <T> serverError(
            apiResponseCode: ApiResponseCode = ApiResponseCode.SEVER_UNHANDLED_EXCEPTION,
            payload: T? = null
        ): Mono<ResponseEntity<ApiResponse<T>>> = Mono.just(
            ResponseEntity
                .status(apiResponseCode.status)
                .body(ApiResponse(false, apiResponseCode.code, apiResponseCode.message, payload))
        )
    }
}