package lkdcode.transaction.domains.company.application.port.out

import reactor.core.publisher.Mono

interface IdentityAuthenticationQueryPort {
    fun isVerified(signUpKey: String): Mono<Boolean>
}

interface IdentityAuthenticationCommandPort {
    fun remove(signUpKey: String): Mono<Boolean>
}