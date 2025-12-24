package com.sb.framework.security.login

import com.sb.adapter.auth.input.web.TokenIssueWebAdapter
import com.sb.application.auth.ports.input.command.LoginUsecase
import com.sb.framework.api.ApiResponseCode
import com.sb.framework.api.ApiResponseWriter
import com.sb.framework.mono.monoSuspend
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class UserLoginSuccessHandler(
    private val loginUsecase: LoginUsecase,
    private val tokenIssueWebAdapter: TokenIssueWebAdapter,
    private val apiResponseWriter: ApiResponseWriter,
) : ServerAuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange,
        authentication: Authentication
    ): Mono<Void> {
        val userAuthentication = authentication.principal as UserAuthentication

        return tokenIssueWebAdapter
            .issue(webFilterExchange, userAuthentication)
            .then(monoSuspend { loginUsecase.onLoginSuccess(userAuthentication.userId) })
            .then(
                apiResponseWriter
                    .writeResponse(
                        response = webFilterExchange.exchange.response,
                        success = true,
                        apiResponseCode = ApiResponseCode.SUCCESS_CREDENTIALS,
                        payload = null
                    )
            )
    }
}