package com.sb.adapter.plan.output.query

import com.sb.adapter.plan.output.infrastructure.r2dbc.repository.CreditTransactionR2dbcRepository
import com.sb.application.credit.ports.output.query.CreditTransactionQueryPort
import com.sb.domain.credit.value.TransactionReason
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneOffset

@Component
class CreditTransactionQueryAdapter(
    private val repository: CreditTransactionR2dbcRepository,
) : CreditTransactionQueryPort {

    override suspend fun existsByUserIdAndReasonAndDate(
        userId: Long,
        reason: TransactionReason,
        date: LocalDate,
    ): Boolean {
        val startOfDay = date.atStartOfDay().toInstant(ZoneOffset.UTC)
        val endOfDay = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC)

        return repository
            .existsByUserIdAndReasonAndCreatedAtBetween(
                userId = userId,
                reason = reason.name,
                startAt = startOfDay,
                endAt = endOfDay,
            )
            .awaitSingle()
    }
}