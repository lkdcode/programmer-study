package lkdcode.transaction.domains.company.application.validator

import lkdcode.transaction.domains.company.application.port.out.CompanyQueryPort
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CompanyValidator(
    private val companyQueryPort: CompanyQueryPort,
) {

    fun validateDuplicateBrn(brn: String): Mono<Void> =
        companyQueryPort
            .existsByBrn(brn)
            .flatMap { exists ->
                if (exists) {
                    Mono.error(IllegalArgumentException("이미 등록된 사업자 등록번호입니다."))
                } else {
                    Mono.empty()
                }
            }
}