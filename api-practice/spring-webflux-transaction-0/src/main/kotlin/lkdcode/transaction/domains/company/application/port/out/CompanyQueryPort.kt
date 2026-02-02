package lkdcode.transaction.domains.company.application.port.out

import reactor.core.publisher.Mono

interface CompanyQueryPort {
    fun existsByCode(code: String): Mono<Boolean>
    fun existsByBrn(brn: String): Mono<Boolean>
}