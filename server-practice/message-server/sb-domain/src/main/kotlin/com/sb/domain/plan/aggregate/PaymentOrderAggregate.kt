package com.sb.domain.plan.aggregate

import com.sb.domain.exception.domainRequire
import com.sb.domain.plan.entity.PaymentOrder
import com.sb.domain.plan.exception.PlanErrorCode
import com.sb.domain.plan.value.Credit
import com.sb.domain.plan.value.Money
import com.sb.domain.plan.value.PaymentMethod
import java.time.Instant

class PaymentOrderAggregate private constructor(
    private var order: PaymentOrder
) {
    val getOrder: PaymentOrder get() = order

    fun markPaid(providerPaymentId: String, now: Instant = Instant.now()): PaymentOrderAggregate {
        domainRequire(order.status == PaymentOrder.Status.PENDING, PlanErrorCode.PAYMENT_ORDER_INVALID_STATE) { "결제 완료 처리는 PENDING 상태에서만 가능합니다." }
        domainRequire(providerPaymentId.isNotBlank(), PlanErrorCode.PAYMENT_ORDER_PROVIDER_PAYMENT_ID_REQUIRED) { "providerPaymentId는 필수입니다." }
        order = order.copy(
            status = PaymentOrder.Status.PAID,
            providerPaymentId = providerPaymentId,
            updatedAt = now,
        )
        return this
    }

    fun markFailed(now: Instant = Instant.now()): PaymentOrderAggregate {
        domainRequire(order.status == PaymentOrder.Status.PENDING, PlanErrorCode.PAYMENT_ORDER_INVALID_STATE) { "결제 실패 처리는 PENDING 상태에서만 가능합니다." }
        order = order.copy(status = PaymentOrder.Status.FAILED, updatedAt = now)
        return this
    }

    fun cancel(now: Instant = Instant.now()): PaymentOrderAggregate {
        domainRequire(order.status == PaymentOrder.Status.PENDING, PlanErrorCode.PAYMENT_ORDER_INVALID_STATE) { "결제 취소는 PENDING 상태에서만 가능합니다." }
        order = order.copy(status = PaymentOrder.Status.CANCELLED, updatedAt = now)
        return this
    }

    companion object {
        fun restore(order: PaymentOrder): PaymentOrderAggregate = PaymentOrderAggregate(order)

        fun create(
            userId: Long,
            method: PaymentMethod,
            amount: Money,
            creditsToGrant: Credit,
            now: Instant = Instant.now(),
        ): PaymentOrderAggregate {
            domainRequire(userId > 0, PlanErrorCode.PAYMENT_ORDER_INVALID_USER_ID) { "userId가 올바르지 않습니다." }
            domainRequire(creditsToGrant.value > 0, PlanErrorCode.PAYMENT_ORDER_INVALID_CREDITS_TO_GRANT) { "부여할 크레딧은 1 이상이어야 합니다." }

            return PaymentOrderAggregate(
                PaymentOrder(
                    id = generateOrderId(),
                    userId = userId,
                    method = method,
                    amount = amount,
                    creditsToGrant = creditsToGrant,
                    status = PaymentOrder.Status.PENDING,
                    providerPaymentId = null,
                    createdAt = now,
                    updatedAt = now,
                )
            )
        }

        private fun generateOrderId(): PaymentOrder.PaymentOrderId =
            PaymentOrder.PaymentOrderId(Instant.now().toEpochMilli())
    }
}