package com.sb.domain.user.value

import kotlin.random.Random

@JvmInline
value class EmailVerificationToken private constructor(
    val value: String
) {
    init {
        require(value.isNotBlank()) { REQUIRE_MESSAGE }
        require(value.length == LENGTH) { INVALID_LENGTH_MESSAGE }
        require(value.all { it.isDigit() }) { INVALID_FORMAT_MESSAGE }
    }

    companion object {
        const val LENGTH = 8
        const val REQUIRE_MESSAGE = "이메일 인증 토큰은 필수입니다."
        const val INVALID_LENGTH_MESSAGE = "이메일 인증 토큰은 ${LENGTH}자리여야 합니다."
        const val INVALID_FORMAT_MESSAGE = "이메일 인증 토큰은 숫자만 가능합니다."

        fun of(value: String): EmailVerificationToken = EmailVerificationToken(value.trim())

        fun generate(random: Random = Random.Default): EmailVerificationToken {
            val code = buildString(LENGTH) {
                repeat(LENGTH) { append(random.nextInt(0, 10)) }
            }
            return EmailVerificationToken(code)
        }
    }
}