package com.sb.domain.credit.model

import com.sb.domain.credit.value.Credit
import java.time.Instant

data class NewCreditWallet(
    val userId: Long,
    val balance: Credit,
    val createdAt: Instant,
    val updatedAt: Instant,
) {

    companion object {
        fun create(userId: Long, now: Instant = Instant.now()): NewCreditWallet =
            NewCreditWallet(
                userId = userId,
                balance = Credit.zero(),
                createdAt = now,
                updatedAt = now,
            )
    }
}