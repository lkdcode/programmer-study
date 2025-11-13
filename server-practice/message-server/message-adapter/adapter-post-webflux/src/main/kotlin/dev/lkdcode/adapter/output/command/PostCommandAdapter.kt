package dev.lkdcode.adapter.output.command

import dev.lkdcode.application.ports.output.command.PostCommandPort
import dev.lkdcode.domain.aggregate.PostAggregate
import org.springframework.stereotype.Service

@Service
class PostCommandAdapter(

) : PostCommandPort {

    override fun save(aggregate: PostAggregate): PostAggregate {
        REPOSITORY.add(aggregate)
        return aggregate
    }

    companion object {
        private val REPOSITORY: MutableList<PostAggregate> = mutableListOf()
    }
}