package com.sb.domain.plan.value

import java.math.BigDecimal
import java.math.RoundingMode

data class Money(
    val amount: BigDecimal,
    val currency: Currency,
) {
    init {
        require(amount >= BigDecimal.ZERO) { REQUIRE_MESSAGE }
        require(amount.scale() <= MAX_SCALE) { SCALE_MESSAGE }
    }

    fun plus(other: Money): Money {
        require(currency == other.currency) { CURRENCY_MESSAGE }
        return copy(amount = (amount + other.amount).setScale(MAX_SCALE, RoundingMode.UNNECESSARY))
    }

    fun minus(other: Money): Money {
        require(currency == other.currency) { CURRENCY_MESSAGE }
        val next = amount - other.amount
        require(next >= BigDecimal.ZERO) { NEGATIVE_MESSAGE }
        return copy(amount = next.setScale(MAX_SCALE, RoundingMode.UNNECESSARY))
    }

    companion object {
        const val MAX_SCALE = 0
        const val REQUIRE_MESSAGE = "금액은 0 이상이어야 합니다."
        const val NEGATIVE_MESSAGE = "금액은 0 미만이 될 수 없습니다."
        const val CURRENCY_MESSAGE = "통화가 일치하지 않습니다."
        const val SCALE_MESSAGE = "금액 단위가 올바르지 않습니다."

        fun krw(amount: Long): Money = Money(BigDecimal.valueOf(amount).setScale(MAX_SCALE), Currency.KRW)
    }
}