package com.sb.domain.credit.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.credit.exception.CreditErrorCode
import java.math.BigDecimal
import java.math.RoundingMode

data class Money(
    val amount: BigDecimal,
    val currency: Currency,
) {
    init {
        domainRequire(amount >= BigDecimal.ZERO, CreditErrorCode.MONEY_NEGATIVE) { REQUIRE_MESSAGE }
        domainRequire(amount.scale() <= MAX_SCALE, CreditErrorCode.MONEY_SCALE_INVALID) { SCALE_MESSAGE }
    }

    fun plus(other: Money): Money {
        domainRequire(currency == other.currency, CreditErrorCode.MONEY_CURRENCY_MISMATCH) { CURRENCY_MESSAGE }
        return copy(amount = (amount + other.amount).setScale(MAX_SCALE, RoundingMode.UNNECESSARY))
    }

    fun minus(other: Money): Money {
        domainRequire(currency == other.currency, CreditErrorCode.MONEY_CURRENCY_MISMATCH) { CURRENCY_MESSAGE }
        val next = amount - other.amount
        domainRequire(next >= BigDecimal.ZERO, CreditErrorCode.MONEY_NEGATIVE_AFTER_MINUS) { NEGATIVE_MESSAGE }
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