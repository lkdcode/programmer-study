package com.sb.domain.user.value

@JvmInline
value class OAuthSubject private constructor(
    val value: String
) {
    init {
        require(value.isNotBlank()) { REQUIRE_MESSAGE }
        require(value.length <= MAX_LENGTH) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val MAX_LENGTH = 200
        const val REQUIRE_MESSAGE = "OAuth subject는 필수입니다."
        const val INVALID_LENGTH_MESSAGE = "OAuth subject는 ${MAX_LENGTH}자를 초과할 수 없습니다."

        fun of(value: String): OAuthSubject = OAuthSubject(value.trim())
    }
}