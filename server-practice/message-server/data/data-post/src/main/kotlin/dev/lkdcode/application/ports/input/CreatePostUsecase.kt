package dev.lkdcode.application.ports.input

import dev.lkdcode.domain.aggregate.PostAggregate
import dev.lkdcode.domain.value.Author

interface CreatePostUsecase {
    fun create(dto: CreatePostDTO): PostAggregate
}

data class CreatePostDTO(
    val title: String,
    val content: String,
    val author: Author,
)