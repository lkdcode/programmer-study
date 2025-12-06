package cool.hdd.domain.post.service

import cool.hdd.domain.post.aggregate.PostAggregate
import cool.hdd.domain.post.entity.Post
import cool.hdd.domain.post.value.Author
import cool.hdd.domain.post.value.PostStatus
import java.time.Instant

class PostDeletionService private constructor(
    private val post: Post,
    private val author: Author,
    private val now: Instant,
) {

    fun validateAuthor(): PostDeletionService = apply {
        require(post.author == author || author.canDelete()) {
            "작성자 또는 관리자만 삭제할 수 있습니다."
        }
        require(author.isNotLocked) { "잠긴 사용자는 삭제할 수 없습니다." }
        require(author.isValid) { "유효하지 않은 사용자입니다." }
    }

    fun validateStatus(): PostDeletionService = apply {
        require(post.status != PostStatus.DELETED) { "이미 삭제된 글입니다." }
    }

    fun deleted(): PostAggregate {
        return PostAggregate.from(post)
            .delete(
                author = author,
                now = now
            )
    }

    companion object {
        fun execute(
            post: Post,
            author: Author,
            now: Instant,
            policy: PostDeletionService.() -> Unit
        ): PostDeletionService = PostDeletionService(
            post = post,
            author = author,
            now = now
        ).apply(policy)
    }
}
