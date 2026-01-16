package com.sb.application.credit.ports.output.query

import com.sb.domain.credit.aggregate.CreditWalletAggregate
import com.sb.domain.credit.entity.CreditWallet

interface CreditWalletQueryPort {
    suspend fun loadByUserId(userId: Long): CreditWalletAggregate
    suspend fun loadByWalletId(walletId: CreditWallet.WalletId): CreditWalletAggregate
}