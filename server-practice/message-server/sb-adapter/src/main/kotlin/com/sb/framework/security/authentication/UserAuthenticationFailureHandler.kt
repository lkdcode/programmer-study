package com.sb.framework.security.authentication

import com.sb.framework.api.ApiResponseCode
import com.sb.framework.api.ApiResponseWriter
import com.sb.framework.log.logInfo
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserAuthenticationFailureHandler(
    private val apiResponseWriter: ApiResponseWriter
) : ServerAuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        webFilterExchange: WebFilterExchange,
        exception: AuthenticationException,
    ): Mono<Void> =
        apiResponseWriter.writeResponse<Unit>(
            response = webFilterExchange.exchange.response,
            success = false,
            apiResponseCode = ApiResponseCode.findByCode(exception.message!!) ?: ApiResponseCode.INVALID_CREDENTIALS,
        ).doOnSuccess {
            logInfo("$exception")
        }
}