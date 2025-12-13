package com.sb.domain.user.value

@JvmInline
value class Nickname private constructor(
    val value: String
) {
    init {
        require(value.isNotBlank()) { REQUIRE_MESSAGE }
        require(value.length in MIN_LENGTH..MAX_LENGTH) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val MIN_LENGTH = 2
        const val MAX_LENGTH = 20

        const val REQUIRE_MESSAGE = "닉네임은 필수입니다."
        const val INVALID_LENGTH_MESSAGE = "닉네임은 ${MIN_LENGTH}~${MAX_LENGTH}자여야 합니다."

        fun of(value: String): Nickname = Nickname(value.trim())
    }
}