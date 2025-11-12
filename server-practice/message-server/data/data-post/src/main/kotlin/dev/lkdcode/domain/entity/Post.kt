package dev.lkdcode.domain.entity

import dev.lkdcode.domain.value.Author
import dev.lkdcode.domain.value.Content
import dev.lkdcode.domain.value.PostStatus
import dev.lkdcode.domain.value.Title
import java.time.Instant

data class Post(
    val id: PostId,

    val title: Title,
    val content: Content,
    val author: Author,
    val status: PostStatus,

    val viewCount: Int = 0,
    val createdAt: Instant,
    val updatedAt: Instant,
) {

    @JvmInline
    value class PostId(val value: Long)

    fun publish(
        publisher: Author
    ): Post {
        require(canEdit(publisher)) { "게시글 게시 권한이 없습니다." }
        require(status == PostStatus.DRAFT) { "임시 저장 상태에서만 게시할 수 있습니다." }

        return copy(
            status = PostStatus.PUBLISHED,
            updatedAt = Instant.now()
        )
    }

    fun update(
        newTitle: Title,
        newContent: Content,
        editor: Author,
    ): Post {
        require(canEdit(editor)) { "게시글 수정 권한이 없습니다." }
        require(status.canEdit()) { "${status.description} 상태는 수정할 수 없습니다." }

        return copy(
            title = newTitle,
            content = newContent,
            updatedAt = Instant.now()
        )
    }

    fun delete(deleter: Author): Post {
        require(canDelete(deleter)) { "게시글 삭제 권한이 없습니다." }

        return copy(
            status = PostStatus.DELETED,
            updatedAt = Instant.now()
        )
    }

    private fun canEdit(editor: Author): Boolean =
        editor.userId == author.userId || editor.isAdmin()

    private fun canDelete(deleter: Author): Boolean =
        deleter.userId == author.userId || deleter.canDelete()
}