package com.sb.framework.security.authentication

import com.sb.adapter.user.output.infrastructure.r2dbc.repository.UserR2dbcRepository
import com.sb.framework.security.jwt.reactive.ReactiveJwtService
import com.sb.framework.security.login.policy.AuthenticationPolicy
import com.sb.framework.security.login.repository.SecurityUserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class UserReactiveAuthenticationManager(
    private val reactiveJwtService: ReactiveJwtService,
    private val securityUserRepository: SecurityUserRepository,
    private val authenticationPolicy: AuthenticationPolicy,
    private val userR2dbcRepository: UserR2dbcRepository,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token = authentication.credentials as? String
            ?: return Mono.error(BadCredentialsException("Invalid-UserApiReactiveAuthenticationManager1"))

        return reactiveJwtService
            .isValidAccessToken(token)
            .flatMap { valid ->
                if (!valid) return@flatMap Mono.error(BadCredentialsException("Invalid-UserApiReactiveAuthenticationManager2"))

                if (!valid) {
                    Mono.error(BadCredentialsException("Invalid-UserApiReactiveAuthenticationManager3"))
                } else {
                    reactiveJwtService
                        .getUsername(token)
                        .switchIfEmpty(Mono.error(BadCredentialsException("Invalid Username Claim")))
                }
            }
            .flatMap {
                userR2dbcRepository
                    .findByEmailAndIsDeletedFalse(it)
                    .switchIfEmpty(Mono.error(BadCredentialsException("Invalid-UserApiReactiveAuthenticationManager4")))
                    .flatMap { entity ->
                        reactiveJwtService
                            .parsePrefix(token)
                            .map { parsedToken ->
                                UsernamePasswordAuthenticationToken(
                                    UserAuthentication(
                                        id = entity.id!!,
                                        role = listOf(SimpleGrantedAuthority(entity.role.name)),
                                        loginId = entity.email,
                                        encodedPassword = entity.password,
                                        isNotDeleted = entity.isNotDeleted(),
                                        isNonLocked = entity.isNonLocked,
                                    ),
                                    null,
                                    listOf(SimpleGrantedAuthority(entity.role.name))
                                )
                            }
                    }
            }
    }
}