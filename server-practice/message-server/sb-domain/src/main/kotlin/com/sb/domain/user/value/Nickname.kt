package com.sb.domain.user.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.user.exception.UserErrorCode

@JvmInline
value class Nickname private constructor(
    val value: String
) {
    init {
        domainRequire(value.isNotBlank(), UserErrorCode.NICKNAME_REQUIRED) { REQUIRE_MESSAGE }
        domainRequire(value.length in MIN_LENGTH..MAX_LENGTH, UserErrorCode.NICKNAME_INVALID_LENGTH) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val MIN_LENGTH = 1
        const val MAX_LENGTH = 20

        const val REQUIRE_MESSAGE = "닉네임은 필수입니다."
        const val INVALID_LENGTH_MESSAGE = "닉네임은 ${MIN_LENGTH}~${MAX_LENGTH}자여야 합니다."

        fun of(value: String): Nickname = Nickname(value.trim())
    }
}