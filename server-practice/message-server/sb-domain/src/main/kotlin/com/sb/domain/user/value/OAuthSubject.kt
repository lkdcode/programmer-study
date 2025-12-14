package com.sb.domain.user.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.user.exception.UserErrorCode

@JvmInline
value class OAuthSubject private constructor(
    val value: String
) {
    init {
        domainRequire(value.isNotBlank(), UserErrorCode.OAUTH_SUBJECT_REQUIRED) { REQUIRE_MESSAGE }
        domainRequire(value.length <= MAX_LENGTH, UserErrorCode.OAUTH_SUBJECT_INVALID_LENGTH) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val MAX_LENGTH = 200
        const val REQUIRE_MESSAGE = "OAuth subject는 필수입니다."
        const val INVALID_LENGTH_MESSAGE = "OAuth subject는 ${MAX_LENGTH}자를 초과할 수 없습니다."

        fun of(value: String): OAuthSubject = OAuthSubject(value.trim())
    }
}