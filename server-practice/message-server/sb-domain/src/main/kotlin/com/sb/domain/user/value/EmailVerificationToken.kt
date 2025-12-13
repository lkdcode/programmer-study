package com.sb.domain.user.value

import java.util.UUID

@JvmInline
value class EmailVerificationToken private constructor(
    val value: String
) {
    init {
        require(value.isNotBlank()) { REQUIRE_MESSAGE }
        require(value.length <= MAX_LENGTH) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val MAX_LENGTH = 200
        const val REQUIRE_MESSAGE = "이메일 인증 토큰은 필수입니다."
        const val INVALID_LENGTH_MESSAGE = "이메일 인증 토큰은 ${MAX_LENGTH}자를 초과할 수 없습니다."

        fun of(value: String): EmailVerificationToken = EmailVerificationToken(value.trim())
        fun generate(): EmailVerificationToken = EmailVerificationToken(UUID.randomUUID().toString())
    }
}


