package lkdcode.transaction.domains.company.application.usecase

import lkdcode.transaction.domains.company.domain.model.ValidateCompanyModel
import reactor.core.publisher.Mono

interface ValidateCompanyUsecase {
    fun validate(model: ValidateCompanyModel): Mono<Void>
}