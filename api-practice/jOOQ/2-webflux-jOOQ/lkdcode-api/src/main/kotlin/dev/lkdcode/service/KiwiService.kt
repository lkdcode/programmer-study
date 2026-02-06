package dev.lkdcode.service

import dev.lkdcode.infrastructure.entity.Kiwi
import dev.lkdcode.infrastructure.jooq.KiwiJooq
import dev.lkdcode.infrastructure.repository.KiwiRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import kotlin.random.Random

@Service
class KiwiService(
    private val kiwiRepository: KiwiRepository,
    private val kiwiJooq: KiwiJooq,
) {

    @Transactional
    fun insert(count: Int = BULK_SIZE): Mono<Void> {
        val entities = List(count) { randomKiwi() }
        return kiwiRepository.saveAll(entities).then()
    }

    private fun randomKiwi() = Kiwi(
        id = null,
        name = NAME_LIST.random(),
        price = Random.nextInt(1, 10),
    )

    @Transactional
    fun deleteAndFetch(name: String): Mono<List<Kiwi>> =
        kiwiRepository
            .deleteAllByName(name)
            .flatMap { kiwiJooq.findAll().collectList() }

    companion object {
        private const val BULK_SIZE = 10

        private val NAME_LIST = listOf(
            "APPLE",
            "BANANA",
            "TOMATO",
        )
    }
}