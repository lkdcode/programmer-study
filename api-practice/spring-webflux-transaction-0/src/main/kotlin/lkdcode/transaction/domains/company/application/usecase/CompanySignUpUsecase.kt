package lkdcode.transaction.domains.company.application.usecase

import lkdcode.transaction.domains.company.application.model.SignUpCommand
import reactor.core.publisher.Mono

interface CompanySignUpUsecase {
    fun signUp(command: SignUpCommand): Mono<Void>
}