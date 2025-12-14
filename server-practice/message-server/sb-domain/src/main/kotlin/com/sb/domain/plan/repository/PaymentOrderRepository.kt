package com.sb.domain.plan.repository

import com.sb.domain.plan.aggregate.PaymentOrderAggregate
import com.sb.domain.plan.entity.PaymentOrder

interface PaymentOrderRepository {
    suspend fun save(aggregate: PaymentOrderAggregate): PaymentOrderAggregate
    suspend fun delete(id: PaymentOrder.PaymentOrderId)
}