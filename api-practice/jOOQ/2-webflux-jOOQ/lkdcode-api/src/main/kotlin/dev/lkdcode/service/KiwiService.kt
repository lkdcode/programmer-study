package dev.lkdcode.service

import dev.lkdcode.infrastructure.entity.Kiwi
import dev.lkdcode.infrastructure.jooq.KiwiJooq
import dev.lkdcode.infrastructure.repository.KiwiRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono
import kotlin.random.Random

@Service
class KiwiService(
    private val transactionalOperator: TransactionalOperator,
    private val kiwiRepository: KiwiRepository,
    private val kiwiJooq: KiwiJooq,
) {

    fun fetch() = transactionalOperator.transactional(
        kiwiJooq.findAll().collectList()
    )

    fun insert(count: Int = BULK_SIZE): Mono<Void> {
        val entities = List(count) { randomKiwi() }

        return transactionalOperator.transactional(
            kiwiRepository.saveAll(entities).then()
        )
    }

    fun deleteAndFetch(name: String): Mono<List<Kiwi>> =
        transactionalOperator.transactional(
            kiwiRepository
                .deleteAllByName(name)
                .then(kiwiJooq.findAll().collectList())
        )

//    @Transactional
//    fun deleteAndFetch(name: String): Mono<List<Kiwi>> =
//            kiwiRepository
//                .deleteAllByName(name)
//                .then(kiwiJooq.findAll().collectList())

    private fun randomKiwi() = Kiwi(
        id = null,
        name = NAME_LIST.random(),
        price = Random.nextInt(1, 10),
    )

    companion object {
        private const val BULK_SIZE = 10

        private val NAME_LIST = listOf(
            "APPLE",
            "BANANA",
            "TOMATO",
        )
    }
}