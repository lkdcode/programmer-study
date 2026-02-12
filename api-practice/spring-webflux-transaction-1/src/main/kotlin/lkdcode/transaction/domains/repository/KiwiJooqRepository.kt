package lkdcode.transaction.domains.repository

import lkdcode.transaction.domains.model.Kiwi
import lkdcode.transaction.jooq.tables.references.KIWI
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

abstract class KiwiJooqRepository(
    private val dsl: DSLContext,
) {

    open fun findAll(): Flux<Kiwi> =
        Flux
            .from(dsl.select(KIWI.NAME).from(KIWI))
            .map { Kiwi(name = it.get(KIWI.NAME)!!) }
}

@Repository
class KiwiJooqBridgeRepository(dsl: DSLContext) :
    KiwiJooqRepository(dsl)

@Repository
class KiwiJooqNoBridgeRepository(@Qualifier("jooqDslNoBridge") dsl: DSLContext) :
    KiwiJooqRepository(dsl)

@Repository
class KiwiJooqNoSubscriberRepository(@Qualifier("jooqDslNoSubscriber") dsl: DSLContext) :
    KiwiJooqRepository(dsl)

@Repository
class KiwiJooqNoContextNoSubscriberRepository(@Qualifier("jooqDslNoContextNoSubscriber") dsl: DSLContext) :
    KiwiJooqRepository(dsl)