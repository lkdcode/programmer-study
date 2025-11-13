package dev.lkdcode.domain.value

enum class PostStatus(
    val description: String
) {

    DRAFT("임시 저장"),
    PUBLISHED("게시됨"),
    HIDDEN("숨김"),
    DELETED("삭제됨"),

    ;

    fun isVisible(): Boolean = this == PUBLISHED

    fun canEdit(): Boolean = this in setOf(DRAFT, PUBLISHED)
}