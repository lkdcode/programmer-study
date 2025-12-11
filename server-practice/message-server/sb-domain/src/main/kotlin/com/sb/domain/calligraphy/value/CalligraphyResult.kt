package com.sb.domain.calligraphy.value


@JvmInline
value class CalligraphyResult private constructor(
    val value: String
) {
    init {
        require(value.isNotBlank()) { REQUIRE_MESSAGE }
    }

    companion object {
        const val REQUIRE_MESSAGE = "캘리그래피 결과는 필수입니다."

        fun of(value: String): CalligraphyResult = CalligraphyResult(value)
    }
}