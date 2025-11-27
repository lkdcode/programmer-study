package dev.lkdcode.security.login.policy

import reactor.core.publisher.Mono

interface AuthenticationPolicy {
    fun onFailure(loginId: String): Mono<Void>
    fun isAttemptAllowed(loginId: String): Mono<Boolean>
    fun permitAll(): Array<String>
    fun authenticated(): Array<String>
}