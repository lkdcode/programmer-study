package com.sb.application.credit.service

import com.sb.application.credit.ports.input.command.RewardLoginBonusUsecase
import com.sb.application.credit.ports.output.command.CreditTransactionCommandPort
import com.sb.application.credit.ports.output.command.CreditWalletCommandPort
import com.sb.application.credit.ports.output.query.CreditTransactionQueryPort
import com.sb.application.credit.ports.output.query.CreditWalletQueryPort
import com.sb.domain.credit.entity.CreditTransaction
import com.sb.domain.credit.value.CreditRewardType
import com.sb.domain.credit.value.TransactionReason
import com.sb.domain.credit.value.TransactionType
import com.sb.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneOffset

@Service
@Transactional
class RewardLoginBonusService(
    private val walletQueryPort: CreditWalletQueryPort,
    private val walletCommandPort: CreditWalletCommandPort,
    private val txCommandPort: CreditTransactionCommandPort,
    private val txQueryPort: CreditTransactionQueryPort,
) : RewardLoginBonusUsecase {

    override suspend fun reward(userId: User.UserId) {
        val today = LocalDate.now(ZoneOffset.UTC)
        val alreadyRewarded = txQueryPort.existsByUserIdAndReasonAndDate(
            userId = userId.value,
            reason = TransactionReason.LOGIN_BONUS,
            date = today,
        )

        if (alreadyRewarded) return

        val wallet = walletQueryPort.loadByUserId(userId.value)

        val tx = wallet.charge(
            CreditRewardType.LOGIN_BONUS.amount,
            CreditTransaction.TransactionId.generate(),
            TransactionReason.LOGIN_BONUS,
            TransactionType.CREDIT,
        )

        walletCommandPort.update(wallet)
        txCommandPort.append(tx)
    }
}