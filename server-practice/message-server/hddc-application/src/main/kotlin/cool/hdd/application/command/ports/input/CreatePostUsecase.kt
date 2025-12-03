package cool.hdd.application.command.ports.input

import cool.hdd.application.command.dto.CreatePostDTO
import cool.hdd.domain.post.aggregate.PostAggregate

interface CreatePostUsecase {
    fun execute(dto: CreatePostDTO): PostAggregate
}