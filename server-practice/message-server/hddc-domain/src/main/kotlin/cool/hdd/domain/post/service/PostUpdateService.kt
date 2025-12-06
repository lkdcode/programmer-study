package cool.hdd.domain.post.service

import cool.hdd.domain.post.aggregate.PostAggregate
import cool.hdd.domain.post.entity.Post
import cool.hdd.domain.post.value.*
import java.time.Instant

class PostUpdateService private constructor(
    private val post: Post,
    private val title: Title,
    private val content: Content,
    private val author: Author,
    private val status: PostStatus,
    private val platformCategory: PlatformCategory,
    private val product: Product,
    private val now: Instant,
) {

    fun validateAuthor(): PostUpdateService = apply {
        require(post.author == author) { "작성자만 수정할 수 있습니다." }
        require(author.isNotLocked) { "잠긴 사용자는 수정할 수 없습니다." }
        require(author.isValid) { "유효하지 않은 사용자입니다." }
    }

    fun validateStatus(): PostUpdateService = apply {
        require(post.status.canEdit()) { "수정할 수 없는 상태입니다." }
    }

    fun validateContent(): PostUpdateService = apply {
        require(title.value.isNotBlank()) { "제목은 필수입니다." }
        require(content.value.isNotBlank()) { "내용은 필수입니다." }
    }

    fun saved(): PostAggregate {
        return PostAggregate.from(post)
            .update(
                title = title,
                content = content,
                author = author,
                status = status,
                platformCategory = platformCategory,
                product = product,
                now = now
            )
    }

    companion object {
        fun execute(
            post: Post,
            title: Title,
            content: Content,
            author: Author,
            status: PostStatus,
            platformCategory: PlatformCategory,
            product: Product,
            now: Instant,
            policy: PostUpdateService.() -> Unit
        ): PostUpdateService = PostUpdateService(
            post = post,
            title = title,
            content = content,
            author = author,
            status = status,
            platformCategory = platformCategory,
            product = product,
            now = now
        ).apply(policy)
    }
}
