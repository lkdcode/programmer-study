package com.sb.framework.security.authentication

import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.aggregate.UserAggregate
import com.sb.framework.mono.monoSuspend
import com.sb.framework.security.jwt.reactive.ReactiveJwtService
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
    private val userQueryPort: UserQueryPort,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> =
        Mono
            .fromCallable { authentication.credentials as String }
            .flatMap { reactiveJwtService.validateAccessToken(it).thenReturn(it) }
            .flatMap { reactiveJwtService.loadUsername(it) }
            .flatMap { email ->
                monoSuspend<UserAggregate> { userQueryPort.loadByEmail(email) }
                    .map { aggregate ->
                        val user = aggregate.snapshot
                        UsernamePasswordAuthenticationToken(
                            UserAuthentication(
                                id = user.id.value,
                                role = listOf(SimpleGrantedAuthority(user.role.name)),
                                loginId = user.email.value,
                                encodedPassword = user.password?.value,
                                isNotDeleted = false,
                                isNonLocked = aggregate.isAccountLocked,
                            ),
                            null,
                            listOf(SimpleGrantedAuthority(user.role.name))
                        )
                    }
            }
            .onErrorResume { Mono.error(BadCredentialsException("Invalid Token")) }
            .map { it }
}