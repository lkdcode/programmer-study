package lkdcode.transaction.domains.company.application.usecase

import lkdcode.transaction.domains.company.domain.model.CompanyId
import lkdcode.transaction.domains.company.domain.model.SignUpModel
import lkdcode.transaction.domains.company.domain.model.UserId
import reactor.core.publisher.Mono

interface CreateUserUsecase {
    fun createUserWhenCompanySignup(companyId: CompanyId, model: SignUpModel): Mono<UserId>
}