package lkdcode.transaction.domains.company.application.usecase

import reactor.core.publisher.Mono

interface CreatePlanUsecase {
    fun saveWhenCompanySignUp(companyId: Long): Mono<Long>
}