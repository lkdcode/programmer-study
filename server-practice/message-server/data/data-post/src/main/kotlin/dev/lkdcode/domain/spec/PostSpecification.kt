package dev.lkdcode.domain.spec

import dev.lkdcode.domain.entity.Post

interface PostSpecification {
    fun isSatisfiedBy(post: Post): Boolean

    fun not(): PostSpecification =
        NotPostSpecification(this)

    infix fun and(other: PostSpecification): PostSpecification =
        AndPostSpecification(this, other)

    infix fun or(other: PostSpecification): PostSpecification =
        OrPostSpecification(this, other)

    private class AndPostSpecification(
        private val left: PostSpecification,
        private val right: PostSpecification
    ) : PostSpecification {

        override fun isSatisfiedBy(post: Post): Boolean =
            left.isSatisfiedBy(post) && right.isSatisfiedBy(post)
    }

    private class OrPostSpecification(
        private val left: PostSpecification,
        private val right: PostSpecification
    ) : PostSpecification {

        override fun isSatisfiedBy(post: Post): Boolean =
            left.isSatisfiedBy(post) || right.isSatisfiedBy(post)
    }

    private class NotPostSpecification(
        private val target: PostSpecification
    ) : PostSpecification {

        override fun isSatisfiedBy(post: Post): Boolean =
            !target.isSatisfiedBy(post)
    }
}