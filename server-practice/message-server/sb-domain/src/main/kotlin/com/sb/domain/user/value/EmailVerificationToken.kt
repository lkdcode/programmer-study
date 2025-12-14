package com.sb.domain.user.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.user.exception.UserErrorCode
import kotlin.random.Random

@JvmInline
value class EmailVerificationToken private constructor(
    val value: String
) {
    init {
        domainRequire(value.isNotBlank(), UserErrorCode.EMAIL_VERIFICATION_TOKEN_REQUIRED) { REQUIRE_MESSAGE }
        domainRequire(value.length == LENGTH, UserErrorCode.EMAIL_VERIFICATION_TOKEN_INVALID_LENGTH) { INVALID_LENGTH_MESSAGE }
        domainRequire(value.all { it.isDigit() }, UserErrorCode.EMAIL_VERIFICATION_TOKEN_INVALID_FORMAT) { INVALID_FORMAT_MESSAGE }
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