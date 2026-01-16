package com.sb.application.credit.ports.output.command

import com.sb.domain.credit.aggregate.CreditWalletAggregate
import com.sb.domain.credit.model.NewCreditWallet

interface CreditWalletCommandPort {
    suspend fun create(newWallet: NewCreditWallet): CreditWalletAggregate
    suspend fun save(aggregate: CreditWalletAggregate): CreditWalletAggregate
    suspend fun update(aggregate: CreditWalletAggregate): CreditWalletAggregate
}