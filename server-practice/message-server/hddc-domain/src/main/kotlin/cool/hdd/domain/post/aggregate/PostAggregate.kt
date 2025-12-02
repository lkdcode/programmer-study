package cool.hdd.domain.post.aggregate

import cool.hdd.domain.post.entity.Post
import cool.hdd.domain.post.entity.Post.Companion.postIdInit
import cool.hdd.domain.post.value.*
import java.time.Instant


class PostAggregate private constructor(
    private var post: Post
) {

    fun update(
        title: Title,
        content: Content,
        author: Author,
        status: PostStatus,
        platformCategory: PlatformCategory,
        product: Product,
        now: Instant,
    ): PostAggregate {
        requireCanEdit(author)

        this.post = post.copy(
            title,
            content,
            status,
            platformCategory,
            product,
            now
        )

        return this
    }

    private fun requireCanEdit(author: Author) {
        require(post.status == PostStatus.PUBLISHED) { "삭제되거나 비활성화된 글은 수정할 수 없습니다." }
        require(post.author == author) { "작성자만 수정할 수 있습니다." }
    }

    companion object {
        fun create(
            title: Title,
            content: Content,
            author: Author,
            status: PostStatus,
            platformCategory: PlatformCategory,
            product: Product,
            now: Instant,
        ) = PostAggregate(
            Post(
                id = postIdInit(),
                title = title,
                content = content,
                author = author,
                status = status,
                platformCategory = platformCategory,
                product = product,
                createdAt = now,
                updatedAt = now,
            )
        )

        fun from(post: Post) = PostAggregate(post)
    }
}