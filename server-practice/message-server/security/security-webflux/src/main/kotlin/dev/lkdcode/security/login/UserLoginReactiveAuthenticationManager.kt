package dev.lkdcode.security.login

import dev.lkdcode.security.login.policy.AuthenticationPolicy
import dev.lkdcode.security.login.repository.SecurityUserRepository
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

        return securityUserRepository
            .findById(loginId)
            .switchIfEmpty(Mono.error(BadCredentialsException("Invalid Authentication")))
            .filter { passwordEncoder.matches(password, it.password) }
            .switchIfEmpty(
                Mono.defer {
                    authenticationPolicy.onFailure(loginId)
                        .then(Mono.error(BadCredentialsException("Invalid Authentication")))
                }
            )
            .filterWhen { authenticationPolicy.isAttemptAllowed(loginId) }
            .switchIfEmpty(Mono.error(BadCredentialsException("Invalid Authentication")))
            .map { UsernamePasswordAuthenticationToken(it.loginId, null, it.role) }
    }
}