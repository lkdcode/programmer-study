package lkdcode.transaction.domains.company.application.validator

import lkdcode.transaction.domains.company.application.port.out.IdentityAuthenticationQueryPort
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class IdentityAuthenticationValidator(
    private val identityAuthenticationQueryPort: IdentityAuthenticationQueryPort,
) {

    fun requireVerified(signUpKey: String): Mono<Void> =
        identityAuthenticationQueryPort
            .isVerified(signUpKey)
            .flatMap { verified ->
                if (!verified) {
                    Mono.error(IllegalArgumentException("본인 인증이 완료되지 않았습니다."))
                } else {
                    Mono.empty()
                }
            }
}