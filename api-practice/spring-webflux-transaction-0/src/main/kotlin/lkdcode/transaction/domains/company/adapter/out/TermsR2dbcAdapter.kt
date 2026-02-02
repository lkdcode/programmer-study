package lkdcode.transaction.domains.company.adapter.out

import lkdcode.transaction.domains.company.application.port.out.TermsCommandPort
import lkdcode.transaction.domains.company.domain.model.UserId
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class TermsR2dbcAdapter(
    private val databaseClient: DatabaseClient,
) : TermsCommandPort {

    override fun saveAll(userId: UserId, termsList: List<String>): Mono<Void> =
        Flux.fromIterable(termsList)
            .flatMap { termsCode ->
                databaseClient
                    .sql("INSERT INTO user_terms (user_id, terms_code) VALUES (:userId, :termsCode)")
                    .bind("userId", userId.value)
                    .bind("termsCode", termsCode)
                    .then()
            }
            .then()
}
