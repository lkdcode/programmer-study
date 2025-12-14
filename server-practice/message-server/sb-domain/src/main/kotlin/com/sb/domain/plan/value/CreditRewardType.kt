package com.sb.domain.plan.value

enum class CreditRewardType(
    val amount: Credit,
    val reason: String,
) {
    SIGNUP_BONUS(Credit.of(600), "SIGNUP_BONUS"),
}