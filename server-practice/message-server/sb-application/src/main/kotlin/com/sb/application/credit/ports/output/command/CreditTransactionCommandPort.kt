package com.sb.application.credit.ports.output.command

import com.sb.domain.credit.aggregate.CreditTransactionAggregate
import com.sb.domain.credit.entity.CreditTransaction

interface CreditTransactionCommandPort {
    suspend fun append(tx: CreditTransaction): CreditTransaction
    suspend fun append(tx: CreditTransactionAggregate): CreditTransactionAggregate
}