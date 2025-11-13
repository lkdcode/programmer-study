package dev.lkdcode.domain.aggregate

import dev.lkdcode.domain.entity.Post
import dev.lkdcode.domain.value.Author
import dev.lkdcode.domain.value.Content
import dev.lkdcode.domain.value.PostStatus
import dev.lkdcode.domain.value.Title
import java.time.Instant

class PostAggregate private constructor(
    private var post: Post
) {
    val getPost: Post get() = post

    fun update(
        newTitle: Title,
        newContent: Content,
        editor: Author,
    ): PostAggregate {
        this.post = post.update(newTitle, newContent, editor)

        return this
    }

    fun delete(
        deleter: Author
    ): PostAggregate {
        this.post = post.delete(deleter)

        return this
    }

    companion object {
        fun create(
            title: Title,
            content: Content,
            author: Author,
        ): PostAggregate = PostAggregate(
            Post(
                id = generatePostId(),
                title = title,
                content = content,
                author = author,
                status = PostStatus.DRAFT,
                viewCount = 0,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
            )
        )

        private fun generatePostId(): Post.PostId =
            Post.PostId(Instant.now().toEpochMilli())
    }
}