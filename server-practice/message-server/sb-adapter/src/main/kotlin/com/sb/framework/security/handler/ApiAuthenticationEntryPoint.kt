package com.sb.framework.security.handler

import com.sb.framework.api.ApiResponseCode
import com.sb.framework.api.ApiResponseWriter
import com.sb.framework.log.logInfo
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class ApiAuthenticationEntryPoint(
    val apiResponseWriter: ApiResponseWriter
) : ServerAuthenticationEntryPoint {

    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException): Mono<Void> =
        apiResponseWriter
            .writeResponse<Unit>(
                response = exchange.response,
                success = false,
                apiResponseCode = ApiResponseCode.AUTHENTICATION_REQUIRED
            )
            .doOnSuccess {
                logInfo("$exchange")
            }
}