package com.sb.adapter.plan.output.cheker

import com.sb.application.credit.ports.output.CreditChecker
import com.sb.application.credit.ports.output.query.CreditWalletQueryPort
import com.sb.domain.credit.entity.CreditWallet
import com.sb.domain.credit.value.Credit
import org.springframework.stereotype.Component

@Component
class CreditCheckAdapter(
    private val creditWalletQueryPort: CreditWalletQueryPort,
) : CreditChecker {

    override suspend fun isEnoughByUserId(
        userId: Long,
        credit: Credit
    ): Boolean {
        val aggregate = creditWalletQueryPort.loadByUserId(userId)

        return aggregate.canPay(credit)
    }

    override suspend fun isEnoughByWalletId(
        wallet: CreditWallet.WalletId,
        credit: Credit
    ): Boolean {
        val aggregate = creditWalletQueryPort.loadByWalletId(wallet)

        return aggregate.canPay(credit)
    }
}