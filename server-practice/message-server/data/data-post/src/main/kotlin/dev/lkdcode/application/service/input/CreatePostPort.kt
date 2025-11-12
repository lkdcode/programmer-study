package dev.lkdcode.application.service.input

import dev.lkdcode.application.ports.input.CreatePostDTO
import dev.lkdcode.application.ports.input.CreatePostUsecase
import dev.lkdcode.domain.aggregate.PostAggregate


class CreatePostPort:CreatePostUsecase {
    override fun create(dto: CreatePostDTO): PostAggregate {
        TODO("Not yet implemented")
    }
}