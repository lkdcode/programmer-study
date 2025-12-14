package com.sb.domain.plan.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.plan.exception.PlanErrorCode

data class PaymentMethod(
    val provider: PaymentProvider,
    val referenceId: String?,
) {
    init {
        domainRequire(referenceId == null || referenceId.isNotBlank(), PlanErrorCode.PAYMENT_METHOD_REFERENCE_BLANK) { REQUIRE_MESSAGE }
        domainRequire(referenceId == null || referenceId.length <= MAX_LENGTH, PlanErrorCode.PAYMENT_METHOD_REFERENCE_TOO_LONG) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val MAX_LENGTH = 200
        const val REQUIRE_MESSAGE = "결제수단 식별자는 비어있을 수 없습니다."
        const val INVALID_LENGTH_MESSAGE = "결제수단 식별자는 ${MAX_LENGTH}자를 초과할 수 없습니다."
    }
}