package cool.hdd.domain.post.repository

import cool.hdd.domain.post.aggregate.PostAggregate
import cool.hdd.domain.post.entity.Post


interface PostRepository {
    fun save(aggregate: PostAggregate): PostAggregate
    fun delete(id: Post.PostId)
}