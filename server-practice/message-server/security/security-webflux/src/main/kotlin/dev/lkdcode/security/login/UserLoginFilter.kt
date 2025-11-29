package dev.lkdcode.security.login

import dev.lkdcode.security.authentication.UserAuthenticationFailureHandler
import org.springframework.http.HttpMethod
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.stereotype.Component


@Component
class UserLoginFilter(
    private val adminLoginConverter: UserLoginConverter,
    private val userLoginSuccessHandler: UserLoginSuccessHandler,
    private val userAuthenticationFailureHandler: UserAuthenticationFailureHandler,
    private val adminReactiveAuthenticationManager: UserLoginReactiveAuthenticationManager,
) {

    fun adminAuthenticationWebFilter(): AuthenticationWebFilter =
        AuthenticationWebFilter(adminReactiveAuthenticationManager)
            .apply {
                setServerAuthenticationConverter(adminLoginConverter)
                setAuthenticationSuccessHandler(userLoginSuccessHandler)
                setAuthenticationFailureHandler(userAuthenticationFailureHandler)
                setRequiresAuthenticationMatcher(
                    ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/api/login")
                )
            }
}