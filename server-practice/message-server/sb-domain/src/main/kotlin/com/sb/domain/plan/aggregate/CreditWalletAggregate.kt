package com.sb.domain.plan.aggregate

import com.sb.domain.exception.domainRequire
import com.sb.domain.plan.entity.CreditTransaction
import com.sb.domain.plan.entity.CreditWallet
import com.sb.domain.plan.exception.PlanErrorCode
import com.sb.domain.plan.value.Credit
import java.time.Instant

class CreditWalletAggregate private constructor(
    private var wallet: CreditWallet
) {
    val getWallet: CreditWallet get() = wallet

    fun earn(
        amount: Credit,
        reason: String,
        reference: String?,
        now: Instant = Instant.now(),
    ): Pair<CreditWalletAggregate, CreditTransaction> {
        domainRequire(reason.isNotBlank(), PlanErrorCode.CREDIT_TX_REASON_REQUIRED) { "사유는 필수입니다." }
        wallet = wallet.copy(
            balance = wallet.balance.plus(amount),
            updatedAt = now,
        )
        return this to CreditTransaction(
            id = generateTxId(),
            userId = wallet.userId,
            type = CreditTransaction.Type.EARN,
            amount = amount,
            reason = reason,
            reference = reference,
            createdAt = now,
        )
    }

    fun spend(
        amount: Credit,
        reason: String,
        reference: String?,
        now: Instant = Instant.now(),
    ): Pair<CreditWalletAggregate, CreditTransaction> {
        domainRequire(reason.isNotBlank(), PlanErrorCode.CREDIT_TX_REASON_REQUIRED) { "사유는 필수입니다." }
        wallet = wallet.copy(
            balance = wallet.balance.minus(amount),
            updatedAt = now,
        )
        return this to CreditTransaction(
            id = generateTxId(),
            userId = wallet.userId,
            type = CreditTransaction.Type.SPEND,
            amount = amount,
            reason = reason,
            reference = reference,
            createdAt = now,
        )
    }

    companion object {
        fun restore(wallet: CreditWallet): CreditWalletAggregate = CreditWalletAggregate(wallet)

        fun create(userId: Long, now: Instant = Instant.now()): CreditWalletAggregate =
            CreditWalletAggregate(
                CreditWallet(
                    id = generateWalletId(),
                    userId = userId,
                    balance = Credit.zero(),
                    createdAt = now,
                    updatedAt = now,
                )
            )

        private fun generateWalletId(): CreditWallet.WalletId =
            CreditWallet.WalletId(Instant.now().toEpochMilli())

        private fun generateTxId(): CreditTransaction.TransactionId =
            CreditTransaction.TransactionId(Instant.now().toEpochMilli())
    }
}