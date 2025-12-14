package com.sb.domain.calligraphy.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.calligraphy.exception.CalligraphyErrorCode


@JvmInline
value class Prompt private constructor(
    val value: String
) {

    init {
        domainRequire(value.isNotBlank(), CalligraphyErrorCode.PROMPT_REQUIRED) { REQUIRE_MESSAGE }
        domainRequire(value.length <= MAX_LENGTH, CalligraphyErrorCode.PROMPT_TOO_LONG) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val REQUIRE_MESSAGE = "명령어는 필수입니다."
        const val MAX_LENGTH = 1_000
        const val INVALID_LENGTH_MESSAGE = "명령어는 ${MAX_LENGTH}자를 초과할 수 없습니다."

        fun of(value: String): Prompt = Prompt(value)
    }
}