package com.sb.framework.security.login.policy

import com.sb.application.user.ports.output.command.UserCommandPort
import com.sb.domain.user.value.Email
import com.sb.framework.security.filter.api.UserApiSecurityFilter
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface AuthenticationPolicy {
    fun onFailure(loginId: String): Mono<Void>
    fun isAttemptAllowed(loginId: String): Mono<Boolean>
    fun permitAll(): Array<String>
    fun authenticated(): Array<String>
}

@Service
class TodoAuthenticationPolicy(
    private val userCommandPort: UserCommandPort
) : AuthenticationPolicy {

    override fun onFailure(loginId: String): Mono<Void> =
        Mono
            .defer {
                mono {
                    userCommandPort.incrementLoginAttemptCount(Email.of(loginId))
                }
            }
            .then()

    override fun isAttemptAllowed(loginId: String): Mono<Boolean> = Mono.just(true)

    override fun permitAll(): Array<String> = UserApiSecurityFilter.PERMIT_ALL_URL

    override fun authenticated(): Array<String> = UserApiSecurityFilter.AUTHENTICATED_URL
}