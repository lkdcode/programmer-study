package com.sb.domain.user.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.user.exception.UserErrorCode

@JvmInline
value class Password private constructor(
    val value: String
) {
    companion object {
        const val MIN_LENGTH = 8
        const val MAX_LENGTH = 64

        const val REQUIRE_MESSAGE = "비밀번호는 필수입니다."
        const val INVALID_LENGTH_MESSAGE = "비밀번호는 ${MIN_LENGTH}~${MAX_LENGTH}자여야 합니다."
        const val POLICY_MESSAGE = "비밀번호는 영어 소문자/대문자/숫자/특수문자를 모두 포함해야 합니다."
        const val WHITESPACE_MESSAGE = "비밀번호에는 공백을 포함할 수 없습니다."

        private val LOWER = Regex("[a-z]")
        private val UPPER = Regex("[A-Z]")
        private val DIGIT = Regex("[0-9]")
        private val SPECIAL = Regex("[^A-Za-z0-9]")
        private val WHITESPACE = Regex("\\s")

        fun of(value: String): Password {
            val raw = value
            domainRequire(raw.isNotBlank(), UserErrorCode.PASSWORD_REQUIRED) { REQUIRE_MESSAGE }
            domainRequire(raw.length in MIN_LENGTH..MAX_LENGTH, UserErrorCode.PASSWORD_INVALID_LENGTH) { INVALID_LENGTH_MESSAGE }
            domainRequire(LOWER.containsMatchIn(raw), UserErrorCode.PASSWORD_POLICY) { POLICY_MESSAGE }
            domainRequire(UPPER.containsMatchIn(raw), UserErrorCode.PASSWORD_POLICY) { POLICY_MESSAGE }
            domainRequire(DIGIT.containsMatchIn(raw), UserErrorCode.PASSWORD_POLICY) { POLICY_MESSAGE }
            domainRequire(SPECIAL.containsMatchIn(raw), UserErrorCode.PASSWORD_POLICY) { POLICY_MESSAGE }
            domainRequire(!WHITESPACE.containsMatchIn(raw), UserErrorCode.PASSWORD_WHITESPACE) { WHITESPACE_MESSAGE }
            return Password(raw)
        }

        fun encrypted(value: String): Password {
            val encrypted = value
            domainRequire(encrypted.isNotBlank(), UserErrorCode.PASSWORD_REQUIRED) { REQUIRE_MESSAGE }
            domainRequire(!WHITESPACE.containsMatchIn(encrypted), UserErrorCode.PASSWORD_WHITESPACE) { WHITESPACE_MESSAGE }
            return Password(encrypted)
        }
    }
}