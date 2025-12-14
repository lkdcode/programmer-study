package com.sb.domain.plan.repository

import com.sb.domain.plan.entity.CreditTransaction

interface CreditTransactionRepository {
    suspend fun append(tx: CreditTransaction): CreditTransaction
}