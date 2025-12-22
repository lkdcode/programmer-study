package com.sb.framework.security.login

import com.sb.framework.security.authentication.UserAuthenticationFailureHandler
import org.springframework.http.HttpMethod
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.stereotype.Component


@Component
class UserLoginFilter(
    private val userLoginConverter: UserLoginConverter,
    private val userLoginSuccessHandler: UserLoginSuccessHandler,
    private val userAuthenticationFailureHandler: UserAuthenticationFailureHandler,
    private val adminReactiveAuthenticationManager: UserLoginReactiveAuthenticationManager,
) {

    fun adminAuthenticationWebFilter(): AuthenticationWebFilter =
        AuthenticationWebFilter(adminReactiveAuthenticationManager)
            .apply {
                setServerAuthenticationConverter(userLoginConverter)
                setAuthenticationSuccessHandler(userLoginSuccessHandler)
                setAuthenticationFailureHandler(userAuthenticationFailureHandler)
                setRequiresAuthenticationMatcher(
                    ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/api/login")
                )
            }
}