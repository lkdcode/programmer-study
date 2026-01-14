package com.sb.domain.credit.aggregate

import com.sb.domain.credit.entity.CreditTransaction
import com.sb.domain.credit.entity.CreditWallet
import com.sb.domain.credit.value.Credit
import com.sb.domain.credit.value.TransactionReason
import com.sb.domain.credit.value.TransactionType

class CreditWalletAggregate private constructor(
    private var wallet: CreditWallet
) {
    val snapshot: CreditWallet get() = wallet

    fun pay(amount: Credit) {
        wallet = wallet.copy(
            balance = wallet.balance.minus(amount),
        )
    }

    fun charge(
        amount: Credit,
        transactionId: CreditTransaction.TransactionId,
        reason: TransactionReason,
        type: TransactionType,
    ): CreditTransaction {
        wallet = wallet.copy(
            balance = wallet.balance.plus(amount),
        )

        return CreditTransaction(
            id = transactionId,
            userId = wallet.userId,
            type = type,
            amount = amount,
            reason = reason,
        )
    }

    fun canPay(credit: Credit): Boolean =
        wallet.balance.value >= credit.value

    companion object {
        fun restore(wallet: CreditWallet): CreditWalletAggregate = CreditWalletAggregate(wallet)
    }
}