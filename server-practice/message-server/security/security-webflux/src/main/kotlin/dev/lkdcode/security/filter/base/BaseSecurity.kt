package dev.lkdcode.security.filter.base

import dev.lkdcode.security.handler.ApiAccessDeniedHandler
import dev.lkdcode.security.handler.ApiAuthenticationEntryPoint
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.stereotype.Component

@Component
class BaseSecurity(
    private val baseAuthenticationHandler: ApiAuthenticationEntryPoint,
    private val baseDeniedHandler: ApiAccessDeniedHandler,
    private val corsSecurity: CorsSecurity,
) {

    fun init(http: ServerHttpSecurity) {
        http.exceptionHandling {
            it.authenticationEntryPoint(baseAuthenticationHandler)
                .accessDeniedHandler(baseDeniedHandler)
        }
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .headers { it.frameOptions { option -> option.disable() } }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .cors { it.configurationSource(corsSecurity.corsConfigurationSource()) }
    }
}