package lkdcode.transaction.domains.repository

import lkdcode.transaction.domains.model.KiwiR2dbcEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface KiwiR2dbcRepository : ReactiveCrudRepository<KiwiR2dbcEntity, Long> {
    fun deleteAllByName(name: String): Mono<Void>
}