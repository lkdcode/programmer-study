package lkdcode.transaction.domains.jooq

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

abstract class PidVerificationSpec : BehaviorSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var transactionalOperator: TransactionalOperator

    @Autowired
    lateinit var databaseClient: DatabaseClient

    fun r2dbcPid(): Mono<Int> =
        databaseClient
            .sql("SELECT pg_backend_pid()")
            .map { row, _ -> row.get(0, Integer::class.java)!!.toInt() }
            .one()

    fun jooqPid(dsl: DSLContext): Mono<Int> =
        Mono.from(dsl.select(DSL.field("pg_backend_pid()", Int::class.java)))
            .map { it.value1() }
}