package com.sb.domain.plan.value

data class PaymentMethod(
    val provider: PaymentProvider,
    val referenceId: String?,
) {
    init {
        require(referenceId == null || referenceId.isNotBlank()) { REQUIRE_MESSAGE }
        require(referenceId == null || referenceId.length <= MAX_LENGTH) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val MAX_LENGTH = 200
        const val REQUIRE_MESSAGE = "결제수단 식별자는 비어있을 수 없습니다."
        const val INVALID_LENGTH_MESSAGE = "결제수단 식별자는 ${MAX_LENGTH}자를 초과할 수 없습니다."
    }
}