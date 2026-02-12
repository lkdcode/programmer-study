package lkdcode.transaction.domains.service

import lkdcode.transaction.domains.model.Kiwi
import lkdcode.transaction.domains.repository.KiwiJooqNoSubscriberRepository
import lkdcode.transaction.domains.repository.KiwiR2dbcRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Service
class KiwiNoSubscriberService(
    private val transactionalOperator: TransactionalOperator,

    private val kiwiR2dbcRepository: KiwiR2dbcRepository,
    private val kiwiJooqNoSubscriberRepository: KiwiJooqNoSubscriberRepository,
) {

    fun deleteByNameThenFindAll(name: String): Mono<List<Kiwi>> =
        transactionalOperator.transactional(
            kiwiR2dbcRepository.deleteAllByName(name)
                .thenMany(kiwiJooqNoSubscriberRepository.findAll())
                .collectList()
        )
}