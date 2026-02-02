package lkdcode.transaction.domains.company.application.port.out

import lkdcode.transaction.domains.company.domain.model.CompanyCode
import lkdcode.transaction.domains.company.domain.model.CompanyId
import lkdcode.transaction.domains.company.domain.model.SignUpModel
import reactor.core.publisher.Mono

interface CompanyCommandPort {
    fun save(code: CompanyCode, model: SignUpModel): Mono<CompanyId>
}