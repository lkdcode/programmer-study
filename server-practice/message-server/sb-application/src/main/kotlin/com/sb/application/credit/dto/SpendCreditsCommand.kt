package com.sb.application.credit.dto

import com.sb.domain.credit.aggregate.CreditTransactionAggregate
import com.sb.domain.credit.entity.CreditTransaction
import com.sb.domain.credit.value.Credit
import com.sb.domain.credit.value.TransactionReason
import com.sb.domain.credit.value.TransactionType

data class SpendCreditsCommand(
    val userId: Long,
    val amount: Credit,
    val type: TransactionType,
    val reason: TransactionReason,
) {

    fun createTransactionAggregate(transactionId: CreditTransaction.TransactionId): CreditTransactionAggregate =
        CreditTransactionAggregate.create(
            transactionId = transactionId,
            userId = userId,
            amount = amount,
            type = type,
            reason = reason,
        )
}