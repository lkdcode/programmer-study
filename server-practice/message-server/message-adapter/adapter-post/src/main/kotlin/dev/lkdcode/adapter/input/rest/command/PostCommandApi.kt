package dev.lkdcode.adapter.input.rest.command

import dev.lkdcode.application.ports.input.command.CreatePostDTO
import dev.lkdcode.application.ports.input.command.CreatePostUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class PostCommandApi(
    private val createPostUsecase: CreatePostUsecase
) {

    @PostMapping("/api/posts")
    fun getCreate(
        @RequestBody request: CreatePostRequest
    ): ResponseEntity<String> {
        createPostUsecase.create(request.convert)

        return ResponseEntity.ok("Success!")
    }
}

data class CreatePostRequest(
    val title: String,
    val content: String,
    val authorId: Long,
) {
    val convert
        get() = CreatePostDTO(
            title = title,
            content = content,
            authorId = authorId,
        )
}