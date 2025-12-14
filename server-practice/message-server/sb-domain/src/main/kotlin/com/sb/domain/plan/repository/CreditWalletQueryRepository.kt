package com.sb.domain.plan.repository

import com.sb.domain.plan.aggregate.CreditWalletAggregate

interface CreditWalletQueryRepository {
    suspend fun findByUserId(userId: Long): CreditWalletAggregate?
}