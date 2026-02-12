package lkdcode.transaction.domains.service

import lkdcode.transaction.domains.model.Kiwi
import lkdcode.transaction.domains.repository.KiwiJooqNoBridgeRepository
import lkdcode.transaction.domains.repository.KiwiR2dbcRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Service
class KiwiNoBridgeService(
    private val transactionalOperator: TransactionalOperator,

    private val kiwiR2dbcRepository: KiwiR2dbcRepository,
    private val kiwiJooqNoBridgeRepository: KiwiJooqNoBridgeRepository,
) {

    fun deleteByNameThenFindAll(name: String): Mono<List<Kiwi>> =
        transactionalOperator.transactional(
            kiwiR2dbcRepository.deleteAllByName(name)
                .thenMany(kiwiJooqNoBridgeRepository.findAll())
                .collectList()
        )
}
