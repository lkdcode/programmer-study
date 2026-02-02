package lkdcode.transaction.domains.company.adapter.out

import lkdcode.transaction.domains.company.application.port.out.IdentityAuthenticationCommandPort
import lkdcode.transaction.domains.company.application.port.out.IdentityAuthenticationQueryPort
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@Repository
class IdentityAuthenticationStubAdapter : IdentityAuthenticationQueryPort, IdentityAuthenticationCommandPort {

    private val store = ConcurrentHashMap<String, Boolean>()

    init {
        store["test-sign-up-key"] = true
    }

    override fun isVerified(signUpKey: String): Mono<Boolean> =
        Mono.fromCallable { store.getOrDefault(signUpKey, false) }

    override fun remove(signUpKey: String): Mono<Boolean> =
        Mono.fromCallable { store.remove(signUpKey) != null }

    fun addVerification(signUpKey: String) {
        store[signUpKey] = true
    }
}