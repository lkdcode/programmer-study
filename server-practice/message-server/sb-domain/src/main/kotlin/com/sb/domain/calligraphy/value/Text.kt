package com.sb.domain.calligraphy.value

import com.sb.domain.calligraphy.exception.CalligraphyErrorCode.TEXT_REQUIRED
import com.sb.domain.calligraphy.exception.CalligraphyErrorCode.TEXT_TOO_LONG
import com.sb.domain.exception.domainRequire

@JvmInline
value class Text private constructor(
    val value: String
) {

    init {
        domainRequire(value.isNotBlank(), TEXT_REQUIRED) { REQUIRE_TEXT }
        domainRequire(value.length <= MAX_LENGTH, TEXT_TOO_LONG) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val REQUIRE_TEXT = "문구는 필수입니다."
        const val MAX_LENGTH = 100
        const val INVALID_LENGTH_MESSAGE = "문구는 ${MAX_LENGTH}자를 초과할 수 없습니다."

        fun of(value: String): Text = Text(value)
    }
}