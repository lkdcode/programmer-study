package com.sb.domain.credit.entity

import com.sb.domain.credit.value.Credit
import com.sb.domain.credit.value.Money
import com.sb.domain.credit.value.PaymentMethod

data class PaymentOrder(
    val id: PaymentOrderId,
    val userId: Long,
    val method: PaymentMethod,
    val amount: Money,
    val creditsToGrant: Credit,
    val status: Status,
    val providerPaymentId: String?,
) {
    @JvmInline
    value class PaymentOrderId(val value: Long)

    enum class Status {
        PENDING, PAID, CANCELLED, FAILED
    }
}