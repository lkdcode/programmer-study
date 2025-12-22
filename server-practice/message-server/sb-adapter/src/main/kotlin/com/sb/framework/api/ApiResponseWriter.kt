package com.sb.framework.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

@Component
class ApiResponseWriter(
    private val objectMapper: ObjectMapper
) {

    fun <T> writeResponse(
        response: ServerHttpResponse,
        success: Boolean,
        apiResponseCode: ApiResponseCode,
        payload: Mono<T>,
        headerList: Map<String, String>? = null
    ): Mono<Void> {
        return payload
            .flatMap { data ->
                headerList?.forEach { (key, value) -> response.headers.add(key, value) }
                response.statusCode = apiResponseCode.status
                response.headers.contentType = MediaType.APPLICATION_JSON
                val buffer = response.bufferFactory().wrap(
                    getBody(
                        success,
                        apiResponseCode,
                        data,
                    )
                )
                response.writeWith(Mono.just(buffer))
            }
    }

    fun <T> writeResponse(
        response: ServerHttpResponse,
        success: Boolean,
        apiResponseCode: ApiResponseCode,
        payload: T? = null,
        headerList: Map<String, String>? = null
    ): Mono<Void> {
        headerList?.forEach { (key, value) -> response.headers.add(key, value) }
        response.statusCode = apiResponseCode.status
        response.headers.contentType = MediaType.APPLICATION_JSON
        val buffer = response.bufferFactory().wrap(
            getBody(
                success,
                apiResponseCode,
                payload,
            )
        )

        return response.writeWith(Mono.just(buffer))
    }

    private fun <T> getBody(
        success: Boolean,
        apiResponseCode: ApiResponseCode,
        payload: T? = null
    ) = objectMapper.writeValueAsString(
        ApiResponse.of(
            success = success,
            apiCode = apiResponseCode,
            payload = payload
        )
    ).toByteArray(StandardCharsets.UTF_8)

}