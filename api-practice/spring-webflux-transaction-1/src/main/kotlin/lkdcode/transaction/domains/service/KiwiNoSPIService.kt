package lkdcode.transaction.domains.service

import lkdcode.transaction.domains.model.Kiwi
import lkdcode.transaction.domains.repository.KiwiJooqNoSPIRepository
import lkdcode.transaction.domains.repository.KiwiR2dbcRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Service
class KiwiNoSPIService(
    private val transactionalOperator: TransactionalOperator,

    private val kiwiR2dbcRepository: KiwiR2dbcRepository,
    private val kiwiJooqNoSPIRepository: KiwiJooqNoSPIRepository,
) {

    fun deleteByNameThenFindAll(name: String): Mono<List<Kiwi>> =
        transactionalOperator.transactional(
            kiwiR2dbcRepository.deleteAllByName(name)
                .thenMany(kiwiJooqNoSPIRepository.findAll())
                .collectList()
        )
}
