package com.sb.domain.credit.value

import com.sb.domain.credit.exception.CreditErrorCode
import com.sb.domain.exception.domainRequire

@JvmInline
value class Credit private constructor(
    val value: Long
) {
    init {
        domainRequire(value >= 0, CreditErrorCode.CREDIT_NEGATIVE) { REQUIRE_MESSAGE }
    }

    fun plus(other: Credit): Credit =
        Credit(value + other.value)

    fun minus(other: Credit): Credit {
        val next = value - other.value
        domainRequire(next >= 0, CreditErrorCode.CREDIT_INSUFFICIENT) { INSUFFICIENT_MESSAGE }
        return Credit(next)
    }

    companion object {
        const val REQUIRE_MESSAGE = "크레딧은 0 이상이어야 합니다."
        const val INSUFFICIENT_MESSAGE = "크레딧이 부족합니다."

        fun of(value: Long): Credit = Credit(value)
        fun zero(): Credit = Credit(0)
    }
}