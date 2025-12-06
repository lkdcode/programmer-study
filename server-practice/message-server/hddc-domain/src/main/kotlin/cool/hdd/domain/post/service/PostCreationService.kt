package cool.hdd.domain.post.service

import cool.hdd.domain.post.aggregate.PostAggregate
import cool.hdd.domain.post.entity.Post
import cool.hdd.domain.post.value.Author
import cool.hdd.domain.post.value.PostStatus

class PostCreationService private constructor(
    val post: Post,
) {

    fun validateStatus(): PostCreationService = apply {
        require(post.status == PostStatus.DRAFT) { "INVALID" }
    }

    fun validateAuthor(validate: Author.() -> Unit): PostCreationService = apply {
        require(post.author.isNotLocked)
        require(post.author.isValid)
    }

    fun saved(): PostAggregate = PostAggregate.from(post)

    companion object {
        fun execute(
            post: Post,
            policy: PostCreationService.() -> Unit
        ): PostCreationService = PostCreationService(post).apply(policy)
    }
}