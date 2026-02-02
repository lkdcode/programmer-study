package lkdcode.transaction.domains.company.application.usecase.service

import lkdcode.transaction.domains.company.application.port.out.UserCommandPort
import lkdcode.transaction.domains.company.application.usecase.CreateUserUsecase
import lkdcode.transaction.domains.company.domain.model.CompanyId
import lkdcode.transaction.domains.company.domain.model.SignUpModel
import lkdcode.transaction.domains.company.domain.model.UserId
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreateUserService(
    private val userCommandPort: UserCommandPort,
) : CreateUserUsecase {

    override fun createUserWhenCompanySignup(companyId: CompanyId, model: SignUpModel): Mono<UserId> =
        userCommandPort.save(companyId, model)
}