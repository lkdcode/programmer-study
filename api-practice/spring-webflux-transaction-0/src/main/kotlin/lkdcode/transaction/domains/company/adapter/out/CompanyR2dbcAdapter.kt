package lkdcode.transaction.domains.company.adapter.out

import lkdcode.transaction.domains.company.application.port.out.CompanyCommandPort
import lkdcode.transaction.domains.company.application.port.out.CompanyQueryPort
import lkdcode.transaction.domains.company.domain.model.CompanyCode
import lkdcode.transaction.domains.company.domain.model.CompanyId
import lkdcode.transaction.domains.company.domain.model.SignUpModel
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.lang.Long
import kotlin.Boolean
import kotlin.String

@Repository
class CompanyR2dbcAdapter(
    private val databaseClient: DatabaseClient,
) : CompanyCommandPort, CompanyQueryPort {

    override fun save(code: CompanyCode, model: SignUpModel): Mono<CompanyId> =
        databaseClient
            .sql("INSERT INTO company (code, brn, name) VALUES (:code, :brn, :name)")
            .bind("code", code.value)
            .bind("brn", model.brn)
            .bind("name", model.companyName)
            .filter { statement -> statement.returnGeneratedValues("id") }
            .map { readable -> readable.get("id", Long::class.java)!!.toLong() }
            .one()
            .map { CompanyId(it) }

    override fun existsByCode(code: String): Mono<Boolean> =
        databaseClient
            .sql("SELECT COUNT(*) as cnt FROM company WHERE code = :code")
            .bind("code", code)
            .map { readable -> readable.get("cnt", Long::class.java)!!.toLong() > 0 }
            .one()
            .defaultIfEmpty(false)

    override fun existsByBrn(brn: String): Mono<Boolean> =
        databaseClient
            .sql("SELECT COUNT(*) as cnt FROM company WHERE brn = :brn")
            .bind("brn", brn)
            .map { readable -> readable.get("cnt", Long::class.java)!!.toLong() > 0 }
            .one()
            .defaultIfEmpty(false)
}