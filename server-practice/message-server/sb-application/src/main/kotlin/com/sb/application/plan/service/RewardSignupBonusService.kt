package com.sb.application.plan.service

import com.sb.application.plan.ports.input.command.RewardSignupBonusUsecase
import com.sb.application.plan.ports.output.command.CreditTransactionCommandPort
import com.sb.application.plan.ports.output.command.CreditWalletCommandPort
import com.sb.application.plan.ports.output.query.CreditWalletQueryPort
import com.sb.domain.plan.model.NewCreditWallet
import com.sb.domain.plan.value.CreditRewardType
import com.sb.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RewardSignupBonusService(
    private val walletQueryPort: CreditWalletQueryPort,
    private val walletCommandPort: CreditWalletCommandPort,
    private val txCommandPort: CreditTransactionCommandPort,
) : RewardSignupBonusUsecase {

    override suspend fun reward(userId: User.UserId) {
        val wallet = walletQueryPort.findByUserId(userId.value)
            ?: walletCommandPort.create(NewCreditWallet.create(userId.value))

        val (updatedWallet, tx) = wallet.earn(
            amount = CreditRewardType.SIGNUP_BONUS.amount,
            reason = CreditRewardType.SIGNUP_BONUS.reason,
            reference = userId.toString(),
        )

        walletCommandPort.save(updatedWallet)
        txCommandPort.append(tx)
    }
}