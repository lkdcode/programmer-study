package com.sb.domain.credit.model

import com.sb.domain.credit.value.Credit
import com.sb.domain.user.entity.User
import java.time.Instant

data class NewCreditWallet(
    val userId: Long,
    val balance: Credit,
    val createdAt: Instant,
    val updatedAt: Instant,
) {

    companion object {
        fun create(userId: User.UserId, now: Instant = Instant.now()): NewCreditWallet =
            NewCreditWallet(
                userId = userId.value,
                balance = Credit.zero(),
                createdAt = now,
                updatedAt = now,
            )
    }
}