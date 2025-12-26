package com.sb.domain.credit.aggregate

import com.sb.domain.credit.entity.CreditTransaction
import com.sb.domain.credit.entity.CreditWallet
import com.sb.domain.credit.value.Credit
import com.sb.domain.credit.value.TransactionReason
import com.sb.domain.credit.value.TransactionType
import java.time.Instant

class CreditWalletAggregate private constructor(
    private var wallet: CreditWallet
) {
    val snapshot: CreditWallet get() = wallet

    fun pay(amount: Credit) {
        wallet = wallet.copy(
            balance = wallet.balance.minus(amount),
        )
    }

    fun charge(amount: Credit) {
        wallet = wallet.copy(
            balance = wallet.balance.plus(amount),
        )
    }

    fun canPay(credit: Credit): Boolean =
        wallet.balance.value >= credit.value

    fun earn(
        transactionId: CreditTransaction.TransactionId,
        amount: Credit,
        reason: TransactionReason,
        type: TransactionType,
        now: Instant = Instant.now(),
    ): Pair<CreditWalletAggregate, CreditTransaction> {
        wallet = wallet.copy(
            balance = wallet.balance.plus(amount),
        )

        return this to CreditTransaction(
            id = transactionId,
            userId = wallet.userId,
            type = type,
            amount = amount,
            reason = reason,
        )
    }

    fun spend(
        transactionId: CreditTransaction.TransactionId,
        amount: Credit,
        reason: TransactionReason,
        type: TransactionType,
        now: Instant = Instant.now(),
    ): Pair<CreditWalletAggregate, CreditTransaction> {

        wallet = wallet.copy(
            balance = wallet.balance.minus(amount),
        )

        return this to CreditTransaction(
            id = transactionId,
            userId = wallet.userId,
            type = type,
            amount = amount,
            reason = reason,
        )
    }

    companion object {
        fun restore(wallet: CreditWallet): CreditWalletAggregate = CreditWalletAggregate(wallet)

        fun spend(
            userId: Long,
            amount: Credit,
            reason: String,
        ): CreditWalletAggregate {

            TODO()
        }
    }
}