package com.sb.domain.plan.entity

import com.sb.domain.plan.value.Credit
import java.time.Instant

data class CreditWallet(
    val id: WalletId,
    val userId: Long,
    val balance: Credit,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    @JvmInline
    value class WalletId(val value: Long)
}