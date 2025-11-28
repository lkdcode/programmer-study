package dev.lkdcode.security.filter.api

import dev.lkdcode.security.login.UserAuthenticationWebFilterFactory
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.stereotype.Component

@Component
class UserApiSecurityFilter(
    private val userAuthenticationWebFilterFactory: UserAuthenticationWebFilterFactory,
) {

    fun doFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .securityMatcher(ServerWebExchangeMatchers.pathMatchers(*MATCH_URL))
            .authorizeExchange {
                it
                    .pathMatchers(*PERMIT_ALL_URL).permitAll()
                    .pathMatchers(*AUTHENTICATED_URL).authenticated()
                    .anyExchange().denyAll()
            }

            .addFilterAt(
                userAuthenticationWebFilterFactory.authenticationWebFilter(),
                SecurityWebFiltersOrder.AUTHENTICATION
            )

            .build()
    }

    companion object {
        private val MATCH_URL = arrayOf(
            "/api", "/api/**",
        )

        private val PERMIT_ALL_URL = arrayOf(
            "/api/login",
            "/api/logout",
        )

        private val AUTHENTICATED_URL = arrayOf(
            "/api/**",
            "/api"
        )
    }
}