package cool.hdd.domain.post.service

import cool.hdd.domain.post.aggregate.PostAggregate
import cool.hdd.domain.post.model.PostQueryResult
import cool.hdd.domain.post.value.PostStatus

class PostQueryService private constructor(
    private val aggregate: PostAggregate?,
) {

    fun validateExists(): PostQueryService = apply {
        require(aggregate != null) { "게시글을 찾을 수 없습니다." }
    }

    fun validateId(id: PostAggregate.PostId): PostQueryService = apply {
        require(aggregate != null) { "게시글을 찾을 수 없습니다." }
        require(aggregate.getId() == id) { "요청한 ID와 일치하지 않습니다." }
    }

    fun validateNotDeleted(): PostQueryService = apply {
        require(aggregate != null) { "게시글을 찾을 수 없습니다." }
        require(aggregate.getStatus() != PostStatus.DELETED) { "삭제된 게시글입니다." }
    }

    fun validatePublished(): PostQueryService = apply {
        require(aggregate != null) { "게시글을 찾을 수 없습니다." }
        require(aggregate.getStatus() == PostStatus.PUBLISHED) { "게시된 글만 조회할 수 있습니다." }
    }

    fun get(): PostAggregate {
        require(aggregate != null) { "게시글을 찾을 수 없습니다." }
        return aggregate
    }

    fun getOrNull(): PostAggregate? = aggregate

    companion object {
        fun findById(
            id: PostAggregate.PostId,
            aggregate: PostAggregate?,
            policy: PostQueryService.() -> Unit = {}
        ): PostQueryService = PostQueryService(aggregate).apply(policy)

        fun findAll(
            aggregates: PostQueryResult,
            policy: PostQueryService.() -> Unit = {}
        ): PostQueryResult = PostQueryResult.from(
            aggregates.toList().mapNotNull { aggregate ->
                PostQueryService(aggregate).apply(policy).getOrNull()
            }
        )
    }
}