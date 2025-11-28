package dev.lkdcode.security.filter

import dev.lkdcode.security.filter.api.UserApiSecurityFilter
import dev.lkdcode.security.filter.base.BaseSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class ApiSecurityFilter(
    private val baseSecurity: BaseSecurity,
    private val userApiSecurityFilter: UserApiSecurityFilter,
) {

    @Bean
    @Order(0)
    fun apiFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        baseSecurity.init(http)

        return userApiSecurityFilter.doFilterChain(http)
    }
}