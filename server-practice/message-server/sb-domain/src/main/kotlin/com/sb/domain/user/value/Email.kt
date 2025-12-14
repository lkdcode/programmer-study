package com.sb.domain.user.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.user.exception.UserErrorCode

@JvmInline
value class Email private constructor(
    val value: String
) {
    init {
        domainRequire(value.isNotBlank(), UserErrorCode.EMAIL_REQUIRED) { REQUIRE_MESSAGE }
        domainRequire(EMAIL_REGEX.matches(value), UserErrorCode.EMAIL_INVALID) { INVALID_MESSAGE }
    }

    companion object {
        const val REQUIRE_MESSAGE = "이메일은 필수입니다."
        const val INVALID_MESSAGE = "올바른 이메일 형식이 아닙니다."

        private val EMAIL_REGEX = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")

        fun of(value: String): Email = Email(value.trim())
    }
}