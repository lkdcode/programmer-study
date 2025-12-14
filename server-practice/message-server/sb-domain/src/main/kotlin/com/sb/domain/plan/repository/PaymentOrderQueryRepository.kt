package com.sb.domain.plan.repository

import com.sb.domain.plan.aggregate.PaymentOrderAggregate
import com.sb.domain.plan.entity.PaymentOrder

interface PaymentOrderQueryRepository {
    suspend fun findById(id: PaymentOrder.PaymentOrderId): PaymentOrderAggregate?
    suspend fun findByUserId(userId: Long): List<PaymentOrderAggregate>
}