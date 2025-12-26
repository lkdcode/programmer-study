package com.sb.application.credit.service

import com.sb.application.credit.dto.SpendCreditsCommand
import com.sb.application.credit.ports.input.command.SpendCreditsUsecase
import com.sb.application.credit.ports.output.command.CreditTransactionCommandPort
import com.sb.application.credit.ports.output.command.CreditWalletCommandPort
import com.sb.application.credit.ports.output.query.CreditWalletQueryPort
import com.sb.domain.credit.entity.CreditTransaction
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SpendCreditsService(
    private val walletQueryPort: CreditWalletQueryPort,
    private val walletCommandPort: CreditWalletCommandPort,
    private val txCommandPort: CreditTransactionCommandPort,
) : SpendCreditsUsecase {

    override suspend fun spend(command: SpendCreditsCommand) {
        val wallet = walletQueryPort.loadByUserId(command.userId)
        wallet.pay(command.amount)
        walletCommandPort.update(wallet)

        val transaction = command.createTransactionAggregate(CreditTransaction.TransactionId("testID"))
        txCommandPort.append(transaction)
    }
}