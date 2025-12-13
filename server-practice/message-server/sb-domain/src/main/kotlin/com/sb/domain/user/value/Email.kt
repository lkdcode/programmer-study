package com.sb.domain.user.value

@JvmInline
value class Email private constructor(
    val value: String
) {
    init {
        require(value.isNotBlank()) { REQUIRE_MESSAGE }
        require(EMAIL_REGEX.matches(value)) { INVALID_MESSAGE }
    }

    companion object {
        const val REQUIRE_MESSAGE = "이메일은 필수입니다."
        const val INVALID_MESSAGE = "올바른 이메일 형식이 아닙니다."

        private val EMAIL_REGEX = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")

        fun of(value: String): Email = Email(value.trim())
    }
}