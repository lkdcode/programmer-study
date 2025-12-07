package cool.hdd.domain.post.model

import cool.hdd.domain.post.aggregate.PostAggregate

class PostQueryResult private constructor(
    private val aggregates: List<PostAggregate>
) {
    fun toList(): List<PostAggregate> = aggregates

    fun filter(predicate: (PostAggregate) -> Boolean): PostQueryResult =
        PostQueryResult(aggregates.filter(predicate))

    fun isEmpty(): Boolean = aggregates.isEmpty()

    fun isNotEmpty(): Boolean = aggregates.isNotEmpty()

    fun size(): Int = aggregates.size

    companion object {
        fun from(aggregates: List<PostAggregate>): PostQueryResult = PostQueryResult(aggregates)
        fun empty(): PostQueryResult = PostQueryResult(emptyList())
    }
}