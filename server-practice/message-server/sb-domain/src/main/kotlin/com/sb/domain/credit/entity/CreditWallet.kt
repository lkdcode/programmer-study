package com.sb.domain.credit.entity

import com.sb.domain.credit.value.Credit

data class CreditWallet(
    val id: WalletId,
    val userId: Long,
    val balance: Credit,
) {

    @JvmInline
    value class WalletId(val value: Long)
}