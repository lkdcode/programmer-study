package com.sb.framework.security.login

import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.value.Email
import com.sb.framework.mono.monoSuspend
import com.sb.framework.security.authentication.UserAuthentication
import com.sb.framework.security.authentication.userAuthentication
import com.sb.framework.security.login.policy.AuthenticationPolicy
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class UserLoginReactiveAuthenticationManager(
    private val userQueryPort: UserQueryPort,
    private val authenticationPolicy: AuthenticationPolicy,
    private val passwordEncoder: PasswordEncoder,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val loginId = authentication.name
        val password = authentication.credentials.toString()

        return this
            .loadAndThrow(loginId)
            .flatMap { matchesPassword(password, it) }
            .flatMap { validateLoginAllowed(it) }
            .map { UsernamePasswordAuthenticationToken(it, null, it.role) }
    }

    private fun loadAndThrow(loginId: String): Mono<UserAuthentication> =
        monoSuspend<UserAuthentication> {
            userQueryPort
                .loadByEmail(Email.of(loginId))
                .userAuthentication()
        }
            .onErrorResume { Mono.error(BadCredentialsException("Invalid Authentication")) }

    private fun matchesPassword(raw: String, auth: UserAuthentication): Mono<UserAuthentication> =
        Mono
            .fromCallable { passwordEncoder.matches(raw, auth.encodedPassword) }
            .flatMap { matched ->
                if (matched) Mono.just(auth)
                else authenticationPolicy
                    .onFailure(auth.loginId)
                    .then(Mono.error(BadCredentialsException("Invalid Authentication")))
            }

    private fun validateLoginAllowed(auth: UserAuthentication): Mono<UserAuthentication> =
        authenticationPolicy
            .isAttemptAllowed(auth.loginId)
            .flatMap { isLocked ->
                if (isLocked) Mono.error(BadCredentialsException("Invalid Authentication"))
                else Mono.just(auth)
            }
}