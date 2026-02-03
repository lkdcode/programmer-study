package lkdcode.transaction.domains.company.application.port.out

import lkdcode.transaction.domains.company.domain.model.UserId
import reactor.core.publisher.Flux

interface TermsCommandPort {
    fun saveAll(userId: UserId, termsList: List<String>): Flux<Long>
}