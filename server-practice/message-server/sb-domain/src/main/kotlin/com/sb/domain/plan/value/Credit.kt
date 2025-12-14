package com.sb.domain.plan.value

@JvmInline
value class Credit private constructor(
    val value: Long
) {
    init {
        require(value >= 0) { REQUIRE_MESSAGE }
    }

    fun plus(other: Credit): Credit = Credit(value + other.value)

    fun minus(other: Credit): Credit {
        val next = value - other.value
        require(next >= 0) { INSUFFICIENT_MESSAGE }
        return Credit(next)
    }

    companion object {
        const val REQUIRE_MESSAGE = "크레딧은 0 이상이어야 합니다."
        const val INSUFFICIENT_MESSAGE = "크레딧이 부족합니다."

        fun of(value: Long): Credit = Credit(value)
        fun zero(): Credit = Credit(0)
    }
}