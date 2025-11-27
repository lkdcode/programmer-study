package dev.lkdcode.security.authentication

import dev.lkdcode.security.jwt.JwtService
import dev.lkdcode.security.login.policy.AuthenticationPolicy
import dev.lkdcode.security.login.repository.SecurityUserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers


@Component
class UserReactiveAuthenticationManager(
    private val jwtService: JwtService,
    private val securityUserRepository: SecurityUserRepository,
    private val authenticationPolicy: AuthenticationPolicy
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token = authentication.credentials as String

        return this
            .validateToken(token)
            .flatMap { extractUsername(token) }
            .filterWhen { authenticationPolicy.isAttemptAllowed(it) }
            .switchIfEmpty(invalidAuth())
            .flatMap { createAuth(it) }
    }

    private fun validateToken(token: String): Mono<Boolean> =
        Mono
            .fromCallable { jwtService.validateAccessToken(token) }
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap { isValid ->
                if (isValid) Mono.just(true)
                else invalidAuth()
            }

    private fun extractUsername(token: String): Mono<String> =
        Mono
            .fromCallable { jwtService.getUsername(token) }
            .subscribeOn(Schedulers.boundedElastic())

    private fun createAuth(username: String): Mono<Authentication> =
        securityUserRepository
            .findById(username)
            .switchIfEmpty(invalidAuth())
            .map { UsernamePasswordAuthenticationToken(it, null, it.role) }

    private fun <T> invalidAuth(): Mono<T> =
        Mono.defer { Mono.error(BadCredentialsException("Invalid Authentication")) }

}