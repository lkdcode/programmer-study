package dev.lkdcode.infrastructure.jooq

import dev.lkdcode.infrastructure.entity.Kiwi
import dev.lkdcode.jooq.tables.references.JKIWI
import org.jooq.DSLContext
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class KiwiJooq(
    private val dsl: DSLContext,
) {

    fun findAll(): Flux<Kiwi> =
        Flux
            .from(
                dsl
                    .select(
                        JKIWI.ID,
                        JKIWI.NAME,
                        JKIWI.PRICE,
                    )
                    .from(JKIWI)
            )
            .map { record ->
                Kiwi(
                    id = record.get(JKIWI.ID),
                    name = record.get(JKIWI.NAME)!!,
                    price = record.get(JKIWI.PRICE)!!,
                )
            }

    fun findById(id: Long): Mono<Kiwi> =
        Mono
            .from(
                dsl
                    .select(
                        JKIWI.ID,
                        JKIWI.NAME,
                        JKIWI.PRICE,
                    )
                    .from(JKIWI)
                    .where(
                        JKIWI.ID.eq(id)
                    )
            )
            .map { record ->
                Kiwi(
                    id = record.get(JKIWI.ID),
                    name = record.get(JKIWI.NAME)!!,
                    price = record.get(JKIWI.PRICE)!!,
                )
            }
}