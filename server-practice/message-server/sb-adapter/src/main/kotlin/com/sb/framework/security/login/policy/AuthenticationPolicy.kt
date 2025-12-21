package com.sb.framework.security.login.policy

import com.sb.framework.security.filter.api.UserApiSecurityFilter
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

interface AuthenticationPolicy {
    fun onFailure(loginId: String): Mono<Void>
    fun isAttemptAllowed(loginId: String): Mono<Boolean>
    fun permitAll(): Array<String>
    fun authenticated(): Array<String>
}

@Service
class TodoAuthenticationPolicy : AuthenticationPolicy {

    override fun onFailure(loginId: String): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun isAttemptAllowed(loginId: String): Mono<Boolean> = Mono.just(true)

    override fun permitAll(): Array<String> = UserApiSecurityFilter.PERMIT_ALL_URL

    override fun authenticated(): Array<String> = UserApiSecurityFilter.AUTHENTICATED_URL
}