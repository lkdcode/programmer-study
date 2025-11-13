package dev.lkdcode.application.ports.input.command

import dev.lkdcode.domain.entity.Post
import dev.lkdcode.domain.value.Content
import dev.lkdcode.domain.value.Title

interface CreatePostUsecase {
    fun create(dto: CreatePostDTO): Post
}

data class CreatePostDTO(
    val title: String,
    val content: String,
    val authorId: Long,
) {
    val titleValue get() = Title(title)
    val contentValue get() = Content(content)
}