package com.sb.domain.user.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.user.exception.UserErrorCode
import kotlin.random.Random

@JvmInline
value class IdentityVerificationToken private constructor(
    val value: String
) {
    init {
        domainRequire(value.isNotBlank(), UserErrorCode.EMAIL_VERIFICATION_TOKEN_REQUIRED) { REQUIRE_MESSAGE }
        domainRequire(value.length == LENGTH, UserErrorCode.EMAIL_VERIFICATION_TOKEN_INVALID_LENGTH) { INVALID_LENGTH_MESSAGE }
        domainRequire(value.all { it.isDigit() }, UserErrorCode.EMAIL_VERIFICATION_TOKEN_INVALID_FORMAT) { INVALID_FORMAT_MESSAGE }
    }

    companion object {
        const val LENGTH = 8
        const val REQUIRE_MESSAGE = "이메일 인증 코드는 필수입니다."
        const val INVALID_LENGTH_MESSAGE = "이메일 인증 코드는 ${LENGTH}자리여야 합니다."
        const val INVALID_FORMAT_MESSAGE = "이메일 인증 코드는 숫자만 가능합니다."

        fun of(value: String): IdentityVerificationToken = IdentityVerificationToken(value.trim())

        internal fun generate(random: Random = Random.Default): IdentityVerificationToken {
            val code = buildString(LENGTH) {
                repeat(LENGTH) { append(random.nextInt(0, 10)) }
            }
            return IdentityVerificationToken(code)
        }
    }
}