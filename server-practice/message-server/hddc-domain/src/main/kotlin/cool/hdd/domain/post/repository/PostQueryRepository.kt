package cool.hdd.domain.post.repository

import cool.hdd.domain.post.aggregate.PostAggregate
import cool.hdd.domain.post.entity.Post

interface PostQueryRepository {
    fun findById(id: Post.PostId): PostAggregate?
    fun findAll(): List<PostAggregate>
}