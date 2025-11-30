package cool.hdd.domain.post.entity

import cool.hdd.domain.post.value.*
import java.time.Instant

data class Post(
    val id: PostId,

    val title: Title,
    val content: Content,
    val author: Author,
    val status: PostStatus,

    val platformCategory: PlatformCategory,
    val product: Product,

    val viewCount: Int = 0,
    val createdAt: Instant,
    val updatedAt: Instant,
) {

    @JvmInline
    value class PostId(
        val value: Long
    )
}