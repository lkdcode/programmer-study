package lkdcode.transaction.domains.company.application.usecase.service

import lkdcode.transaction.domains.company.application.port.out.PlanCommandPort
import lkdcode.transaction.domains.company.application.usecase.CreatePlanUsecase
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreatePlanService(
    private val planCommandPort: PlanCommandPort,
) : CreatePlanUsecase {

    override fun saveWhenCompanySignUp(companyId: Long): Mono<Long> =
        planCommandPort.saveFreePlan(companyId)
}