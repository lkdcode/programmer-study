package lkdcode.transaction.domains.company.application.port.out

import reactor.core.publisher.Mono

interface PlanCommandPort {
    fun saveFreePlan(companyId: Long): Mono<Long>
}