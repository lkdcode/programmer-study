package cool.hdd.application.command.ports.out

import cool.hdd.domain.post.aggregate.PostAggregate

interface CommandPostPort {
    fun save(postAggregate: PostAggregate): PostAggregate
    fun update(postAggregate: PostAggregate): PostAggregate
}