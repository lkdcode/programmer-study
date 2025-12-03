package cool.hdd.application.command.dto

import cool.hdd.domain.post.aggregate.PostAggregate
import cool.hdd.domain.post.entity.Post
import cool.hdd.domain.post.value.*
import java.time.Instant

data class CreatePostDTO(
    val title: String,
    val content: String,
    val author: Author,
    val postStatus: PostStatus,
    val platformCategory: PlatformCategory,
    val product: Product,
    private val now: Instant = Instant.now()
) {
    fun convert(): PostAggregate = PostAggregate.from(
        Post(
            id = Post.postIdInit(),
            title = Title(title),
            content = Content(content),
            author = author,
            status = postStatus,
            platformCategory = platformCategory,
            product = product,
            createdAt = now,
            updatedAt = now,
        )
    )

    fun convertPost(): Post = Post(
        id = Post.postIdInit(),
        title = Title(title),
        content = Content(content),
        author = author,
        status = postStatus,
        platformCategory = platformCategory,
        product = product,
        createdAt = now,
        updatedAt = now,
    )
}