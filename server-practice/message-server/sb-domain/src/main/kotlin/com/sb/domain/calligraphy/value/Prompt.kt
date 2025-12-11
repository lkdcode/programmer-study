package com.sb.domain.calligraphy.value


@JvmInline
value class Prompt private constructor(
    val value: String
) {

    init {
        require(value.isNotBlank()) { REQUIRE_MESSAGE }
        require(value.length <= MAX_LENGTH) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val REQUIRE_MESSAGE = "명령어는 필수입니다."
        const val MAX_LENGTH = 3_000
        const val INVALID_LENGTH_MESSAGE = "명령어는 ${MAX_LENGTH}자를 초과할 수 없습니다."

        fun of(value: String): Prompt = Prompt(value)
    }
}