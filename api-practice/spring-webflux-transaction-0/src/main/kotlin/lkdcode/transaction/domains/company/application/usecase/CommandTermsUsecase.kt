package lkdcode.transaction.domains.company.application.usecase

import lkdcode.transaction.domains.company.domain.model.UserId
import reactor.core.publisher.Mono

interface CommandTermsUsecase {
    fun saveTermsWhenCompanySignUp(userId: UserId, termsList: List<String>): Mono<Void>
}