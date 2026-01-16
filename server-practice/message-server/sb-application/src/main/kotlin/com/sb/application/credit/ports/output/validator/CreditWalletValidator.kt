package com.sb.application.credit.ports.output.validator

import com.sb.domain.user.entity.User

interface CreditWalletValidator {
    suspend fun requireWalletNotExists(userId: User.UserId)
}
