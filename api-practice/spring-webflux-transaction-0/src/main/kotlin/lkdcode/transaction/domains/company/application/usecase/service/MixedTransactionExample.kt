package lkdcode.transaction.domains.company.application.usecase.service

import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class MixedTransactionExample(
    private val transactionalOperator: TransactionalOperator,
    private val r2dbcPort: R2dbcPort,
    private val legacyJdbcService: LegacyJdbcService,
) {

    fun saveWithMixedTransaction(entity: Entity, data: LegacyData): Mono<Void> =
        transactionalOperator.transactional(
            r2dbcPort
                .save(entity)
                .then(
                    Mono.fromCallable { legacyJdbcService.save(data) }
                        .subscribeOn(Schedulers.boundedElastic())
                )
                .then()
        )

    fun saveWithR2dbcOnly(entity: Entity, migratedData: MigratedData): Mono<Void> =
        transactionalOperator.transactional(
            r2dbcPort
                .save(entity)
                .then(r2dbcPort.saveMigratedData(migratedData))
        )
}

interface R2dbcPort {
    fun save(entity: Entity): Mono<Long>
    fun saveMigratedData(data: MigratedData): Mono<Void>
}

interface LegacyJdbcService {
    fun save(data: LegacyData): Long
}

data class Entity(val id: Long?, val name: String)
data class LegacyData(val value: String)
data class MigratedData(val value: String)