package com.sb.domain.credit.value

enum class CreditRewardType(
    val amount: Credit,
) {
    SIGNUP_BONUS(Credit.of(600)),
    LOGIN_BONUS(Credit.of(200)),
}