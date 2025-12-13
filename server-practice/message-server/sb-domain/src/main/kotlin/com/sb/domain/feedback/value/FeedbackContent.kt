package com.sb.domain.feedback.value

@JvmInline
value class FeedbackContent private constructor(
    val value: String
) {
    init {
        val normalized = normalize(value)
        require(normalized.isNotBlank()) { REQUIRE_MESSAGE }
        require(normalized.length <= MAX_LENGTH) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val MAX_LENGTH = 500

        const val REQUIRE_MESSAGE = "피드백은 필수입니다."
        const val INVALID_LENGTH_MESSAGE = "피드백은 ${MAX_LENGTH}자를 초과할 수 없습니다."

        fun of(value: String): FeedbackContent = FeedbackContent(value)

        private fun normalize(raw: String): String =
            raw.replace("\r\n", "\n")
                .replace('\r', '\n')
                .trim()
    }
}