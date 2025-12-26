package com.sb.domain.credit.aggregate

import com.sb.domain.credit.entity.CreditTransaction
import com.sb.domain.credit.value.Credit
import com.sb.domain.credit.value.TransactionReason
import com.sb.domain.credit.value.TransactionType

class CreditTransactionAggregate private constructor(
    private val transaction: CreditTransaction
) {
    val snapshot: CreditTransaction get() = transaction

    companion object {
        fun create(
            transactionId: CreditTransaction.TransactionId,
            userId: Long,
            amount: Credit,
            type: TransactionType,
            reason: TransactionReason,
        ): CreditTransactionAggregate {

            val transaction = CreditTransaction(
                id = transactionId,
                userId = userId,
                type = type,
                amount = amount,
                reason = reason,
            )

            return CreditTransactionAggregate(transaction)
        }
    }
}