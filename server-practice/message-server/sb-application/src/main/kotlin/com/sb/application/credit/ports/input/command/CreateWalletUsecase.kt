package com.sb.application.credit.ports.input.command

import com.sb.domain.credit.entity.CreditWallet
import com.sb.domain.user.entity.User

interface CreateWalletUsecase {
    suspend fun create(userId: User.UserId): CreditWallet.WalletId
}