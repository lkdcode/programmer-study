package com.sb.framework.security.login

import com.sb.framework.security.authentication.JwtBearerTokenConverter
import com.sb.framework.security.authentication.UserAuthenticationFailureHandler
import com.sb.framework.security.authentication.UserReactiveAuthenticationManager
import com.sb.framework.security.login.policy.AuthenticationPolicy
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange


@Component
class UserAuthenticationWebFilterFactory(
    private val authenticationFailureHandler: UserAuthenticationFailureHandler,
    private val userReactiveAuthenticationManager: UserReactiveAuthenticationManager,
    private val authenticationPolicy: AuthenticationPolicy,
) {

    fun authenticationWebFilter(): AuthenticationWebFilter =
        AuthenticationWebFilter(userReactiveAuthenticationManager)
            .apply {
                setServerAuthenticationConverter(JwtBearerTokenConverter())
                setRequiresAuthenticationMatcher {
                    matches(authenticationPolicy.permitAll(), it)
                        .flatMap { permit ->
                            if (permit.isMatch) ServerWebExchangeMatcher.MatchResult.notMatch()
                            else matches(authenticationPolicy.authenticated(), it)
                        }
                }
                setSecurityContextRepository(WebSessionServerSecurityContextRepository())
                setAuthenticationFailureHandler(authenticationFailureHandler)
            }

    private fun matches(path: Array<String>, target: ServerWebExchange) =
        ServerWebExchangeMatchers.pathMatchers(*path).matches(target)
}