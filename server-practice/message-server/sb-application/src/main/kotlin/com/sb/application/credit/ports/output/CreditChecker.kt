package com.sb.application.credit.ports.output

import com.sb.domain.credit.entity.CreditWallet
import com.sb.domain.credit.value.Credit

interface CreditChecker {
    suspend fun isEnoughByUserId(userId: Long, credit: Credit): Boolean
    suspend fun isEnoughByWalletId(wallet: CreditWallet.WalletId, credit: Credit): Boolean
}