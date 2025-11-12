package dev.lkdcode.domain.repository

import dev.lkdcode.domain.aggregate.PostAggregate
import dev.lkdcode.domain.entity.Post
import dev.lkdcode.domain.spec.PostSpecification
import dev.lkdcode.domain.value.Author

interface PostQueryRepository {
    fun findById(id: Post.PostId): PostAggregate?
    fun findAll(id: Post.PostId): List<PostAggregate>

    fun findBySpec(spec: PostSpecification): List<PostAggregate>
    fun findByAuthor(author: Author): List<PostAggregate>
}