package com.sb.domain.plan.entity

import com.sb.domain.plan.value.Credit
import java.time.Instant

data class CreditTransaction(
    val id: TransactionId,
    val userId: Long,
    val type: Type,
    val amount: Credit,
    val reason: String,
    val reference: String?,
    val createdAt: Instant,
) {
    @JvmInline
    value class TransactionId(val value: Long)

    enum class Type {
        EARN, SPEND
    }
}