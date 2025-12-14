package com.sb.domain.plan.repository

import com.sb.domain.plan.aggregate.CreditWalletAggregate

interface CreditWalletRepository {
    suspend fun save(aggregate: CreditWalletAggregate): CreditWalletAggregate
}