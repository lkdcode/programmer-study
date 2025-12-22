package com.sb.framework.security.handler

import com.sb.framework.api.ApiResponseCode
import com.sb.framework.api.ApiResponseWriter
import com.sb.framework.log.logInfo
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class ApiAccessDeniedHandler(
    val apiResponseWriter: ApiResponseWriter
) : ServerAccessDeniedHandler {

    override fun handle(exchange: ServerWebExchange, denied: AccessDeniedException): Mono<Void> =
        apiResponseWriter
            .writeResponse<Unit>(
                response = exchange.response,
                success = false,
                apiResponseCode = ApiResponseCode.ACCESS_DENIED
            )
            .doOnSuccess {
                logInfo("$denied")
            }
}