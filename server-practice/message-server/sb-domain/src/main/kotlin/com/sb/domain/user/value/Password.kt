package com.sb.domain.user.value

@JvmInline
value class Password private constructor(
    val value: String
) {
    init {
        require(value.isNotBlank()) { REQUIRE_MESSAGE }
        require(value.length in MIN_LENGTH..MAX_LENGTH) { INVALID_LENGTH_MESSAGE }
        require(LOWER.containsMatchIn(value)) { POLICY_MESSAGE }
        require(UPPER.containsMatchIn(value)) { POLICY_MESSAGE }
        require(DIGIT.containsMatchIn(value)) { POLICY_MESSAGE }
        require(SPECIAL.containsMatchIn(value)) { POLICY_MESSAGE }
        require(!WHITESPACE.containsMatchIn(value)) { WHITESPACE_MESSAGE }
    }

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

        fun of(value: String): Password = Password(value)
    }
}