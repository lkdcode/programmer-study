package cool.hdd.domain.post.value

@JvmInline
value class Title(
    val value: String
) {
    init {
        require(value.isNotBlank()) { REQUIRE_MESSAGE }
        require(value.length < MAX_LENGTH) { INVALID_LENGTH_MESSAGE }
    }

    companion object {
        const val REQUIRE_MESSAGE = "제목은 필수입니다."
        const val MAX_LENGTH = 150
        const val INVALID_LENGTH_MESSAGE = "제목은 ${MAX_LENGTH}자를 초과할 수 없습니다."
    }
}