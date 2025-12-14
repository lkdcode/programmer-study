package com.sb.domain.plan.value

enum class UsageType(
    val cost: Credit,
    val reason: String,
) {
    CALLIGRAPHY_GENERATION(Credit.of(200), "CALLIGRAPHY_GENERATION"),
}