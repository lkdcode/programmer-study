package com.sb.application.credit.ports.output.query

import com.sb.domain.credit.value.TransactionReason
import java.time.LocalDate

interface CreditTransactionQueryPort {
    suspend fun existsByUserIdAndReasonAndDate(
        userId: Long,
        reason: TransactionReason,
        date: LocalDate,
    ): Boolean
}
