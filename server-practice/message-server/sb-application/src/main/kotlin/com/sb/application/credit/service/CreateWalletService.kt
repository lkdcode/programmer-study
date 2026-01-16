package com.sb.application.credit.service

import com.sb.application.credit.ports.input.command.CreateWalletUsecase
import com.sb.application.credit.ports.output.command.CreditTransactionCommandPort
import com.sb.application.credit.ports.output.command.CreditWalletCommandPort
import com.sb.application.credit.ports.output.validator.CreditWalletValidator
import com.sb.domain.credit.entity.CreditTransaction
import com.sb.domain.credit.entity.CreditWallet
import com.sb.domain.credit.model.NewCreditWallet
import com.sb.domain.credit.value.CreditRewardType
import com.sb.domain.credit.value.TransactionReason
import com.sb.domain.credit.value.TransactionType
import com.sb.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class CreateWalletService(
    private val walletCommandPort: CreditWalletCommandPort,
    private val txCommandPort: CreditTransactionCommandPort,
    private val walletValidator: CreditWalletValidator,
) : CreateWalletUsecase {

    override suspend fun create(userId: User.UserId): CreditWallet.WalletId {
        walletValidator.requireWalletNotExists(userId)
        val newWallet = NewCreditWallet.create(userId)
        val wallet = walletCommandPort.create(newWallet)

        val tx = wallet.charge(
            CreditRewardType.SIGNUP_BONUS.amount,
            CreditTransaction.TransactionId.generate(),
            TransactionReason.SIGNUP_BONUS,
            TransactionType.CREDIT,
        )

        walletCommandPort.save(wallet)
        txCommandPort.append(tx)

        return wallet.snapshot.id
    }
}
