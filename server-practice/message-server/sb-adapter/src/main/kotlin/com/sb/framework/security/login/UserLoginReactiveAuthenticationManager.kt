package com.sb.framework.security.login

import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.framework.security.authentication.UserAuthentication
import com.sb.framework.security.login.policy.AuthenticationPolicy
import com.sb.framework.security.login.repository.SecurityUserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class UserLoginReactiveAuthenticationManager(
    private val securityUserRepository: SecurityUserRepository,
    private val authenticationPolicy: AuthenticationPolicy,
    private val passwordEncoder: PasswordEncoder,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val loginId = authentication.name
        val password = authentication.credentials.toString()

        return findAndThrow(loginId)
            .flatMap { matchesPassword(password, it) }
            .flatMap { validateLoginAllowed(it) }
            .map { UsernamePasswordAuthenticationToken(it, null, it.role) }
    }

    private fun findAndThrow(loginId: String): Mono<UserAuthentication> =
        securityUserRepository
            .findById(loginId)
            .switchIfEmpty(Mono.error(BadCredentialsException("Invalid Authentication")))

    private fun matchesPassword(raw: String, auth: UserAuthentication): Mono<UserAuthentication> =
        Mono
            .defer { Mono.just(passwordEncoder.matches(raw, auth.encodedPassword)) }
            .switchIfEmpty(
                Mono.defer {
                    authenticationPolicy.onFailure(auth.loginId)
                        .then(Mono.error(BadCredentialsException("Invalid Authentication")))
                }
            )
            .thenReturn(auth)

    private fun validateLoginAllowed(auth: UserAuthentication): Mono<UserAuthentication> =
        authenticationPolicy
            .isAttemptAllowed(auth.loginId)
            .flatMap { result ->
                if (result) Mono.error(BadCredentialsException("Invalid Authentication"))
                else Mono.just(auth)
            }
}