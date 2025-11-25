package dev.lkdcode.security.login.policy

import reactor.core.publisher.Mono

interface AuthenticationPolicy {
    fun failure(loginId: String): Mono<Void>
    fun isAttemptAllowed(loginId: String): Mono<Boolean>
}