package com.sb.domain.plan.entity

import com.sb.domain.plan.value.Credit
import com.sb.domain.plan.value.Money
import com.sb.domain.plan.value.PaymentMethod
import java.time.Instant

data class PaymentOrder(
    val id: PaymentOrderId,
    val userId: Long,
    val method: PaymentMethod,
    val amount: Money,
    val creditsToGrant: Credit,
    val status: Status,
    val providerPaymentId: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    @JvmInline
    value class PaymentOrderId(val value: Long)

    enum class Status {
        PENDING, PAID, CANCELLED, FAILED
    }
}


