package com.sb.domain.credit.entity

import com.sb.domain.credit.value.Credit
import com.sb.domain.credit.value.TransactionReason
import com.sb.domain.credit.value.TransactionType

data class CreditTransaction(
    val id: TransactionId,
    val userId: Long,
    val amount: Credit,

    val type: TransactionType,
    val reason: TransactionReason,
) {

    @JvmInline
    value class TransactionId(val value: String)
}