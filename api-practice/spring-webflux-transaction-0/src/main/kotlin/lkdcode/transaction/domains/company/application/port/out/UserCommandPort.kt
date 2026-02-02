package lkdcode.transaction.domains.company.application.port.out

import lkdcode.transaction.domains.company.domain.model.CompanyId
import lkdcode.transaction.domains.company.domain.model.SignUpModel
import lkdcode.transaction.domains.company.domain.model.UserId
import reactor.core.publisher.Mono

interface UserCommandPort {
    fun save(companyId: CompanyId, model: SignUpModel): Mono<UserId>
}