package dev.lkdcode.application.ports.output.command

import dev.lkdcode.domain.aggregate.PostAggregate

interface PostCommandPort {
    fun save(aggregate: PostAggregate): PostAggregate
}