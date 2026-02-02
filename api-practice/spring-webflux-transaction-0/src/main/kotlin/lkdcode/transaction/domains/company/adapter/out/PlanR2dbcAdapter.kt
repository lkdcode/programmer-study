package lkdcode.transaction.domains.company.adapter.out

import lkdcode.transaction.domains.company.application.port.out.PlanCommandPort
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class PlanR2dbcAdapter(
    private val databaseClient: DatabaseClient,
) : PlanCommandPort {

    override fun saveFreePlan(companyId: Long): Mono<Long> =
        databaseClient
            .sql("INSERT INTO company_plan (company_id, plan_type) VALUES (:companyId, 'FREE')")
            .bind("companyId", companyId)
            .filter { statement -> statement.returnGeneratedValues("id") }
            .map { readable -> readable.get("id", java.lang.Long::class.java)!!.toLong() }
            .one()
}
