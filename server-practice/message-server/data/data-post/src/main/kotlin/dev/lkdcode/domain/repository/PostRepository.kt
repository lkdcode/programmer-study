package dev.lkdcode.domain.repository

import dev.lkdcode.domain.aggregate.PostAggregate
import dev.lkdcode.domain.entity.Post

interface PostRepository {
    fun save(aggregate: PostAggregate): PostAggregate
    fun delete(id: Post.PostId)
}