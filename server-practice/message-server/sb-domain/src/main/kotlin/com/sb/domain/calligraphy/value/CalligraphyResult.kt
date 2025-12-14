package com.sb.domain.calligraphy.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.calligraphy.exception.CalligraphyErrorCode


@JvmInline
value class CalligraphyResult private constructor(
    val value: String
) {
    init {
        domainRequire(value.isNotBlank(), CalligraphyErrorCode.RESULT_REQUIRED) { REQUIRE_MESSAGE }
    }

    companion object {
        const val REQUIRE_MESSAGE = "캘리그래피 결과는 필수입니다."

        fun of(value: String): CalligraphyResult = CalligraphyResult(value)
    }
}