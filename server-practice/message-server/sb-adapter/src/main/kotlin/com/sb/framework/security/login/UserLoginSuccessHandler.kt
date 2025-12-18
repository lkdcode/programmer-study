package com.sb.framework.security.login

import com.sb.framework.security.authentication.UserAuthentication
import com.sb.framework.security.login.repository.SecurityUserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class UserLoginSuccessHandler(
    private val securityUserRepository: SecurityUserRepository,
) : ServerAuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange,
        authentication: Authentication
    ): Mono<Void> {
        val userAuthentication = authentication.principal as UserAuthentication

        return securityUserRepository
            .recordLastLoginTime(userAuthentication.id)
            .then(securityUserRepository.initLoginAttemptCount(userAuthentication.id))
    }
}