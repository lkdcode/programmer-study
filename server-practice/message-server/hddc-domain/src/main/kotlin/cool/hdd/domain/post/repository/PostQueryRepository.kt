package cool.hdd.domain.post.repository

import cool.hdd.domain.post.aggregate.PostAggregate

interface PostQueryRepository {
    fun findById(id: PostAggregate.PostId): PostAggregate?
    fun findAll(): List<PostAggregate>
}