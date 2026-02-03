package lkdcode.transaction.domains.company.adapter.out

import lkdcode.transaction.domains.company.application.port.out.UserCommandPort
import lkdcode.transaction.domains.company.domain.model.CompanyId
import lkdcode.transaction.domains.company.domain.model.SignUpModel
import lkdcode.transaction.domains.company.domain.model.UserId
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.lang.Long

@Repository
class UserR2dbcAdapter(
    private val databaseClient: DatabaseClient,
) : UserCommandPort {

    override fun save(companyId: CompanyId, model: SignUpModel): Mono<UserId> =
        databaseClient
            .sql("INSERT INTO users (company_id, email, password) VALUES (:companyId, :email, :password)")
            .bind("companyId", companyId.value)
            .bind("email", model.email)
            .bind("password", model.password)
            .filter { statement -> statement.returnGeneratedValues("id") }
            .map { readable -> readable.get("id", Long::class.java)!!.toLong() }
            .one()
            .map { UserId(it) }
}