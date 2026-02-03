package lkdcode.transaction.domains.company.application.usecase.service

import lkdcode.transaction.domains.company.application.port.out.TermsCommandPort
import lkdcode.transaction.domains.company.application.usecase.CommandTermsUsecase
import lkdcode.transaction.domains.company.domain.model.UserId
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CommandTermsService(
    private val termsCommandPort: TermsCommandPort,
) : CommandTermsUsecase {

    override fun saveTermsWhenCompanySignUp(userId: UserId, termsList: List<String>): Mono<Void> =
        termsCommandPort.saveAll(userId, termsList).then()
}