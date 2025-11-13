package dev.lkdcode.adapter.input.rest.command

import dev.lkdcode.application.ports.input.command.CreatePostDTO
import dev.lkdcode.application.ports.input.command.CreatePostUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@RestController
class PostCommandApi(
    private val createPostUsecase: CreatePostUsecase
) {

    @PostMapping("/api/posts")
    fun getCreate(
        @RequestBody request: CreatePostRequest
    ): Mono<ResponseEntity<String>> {
        return Mono
            .fromCallable {
                createPostUsecase.create(request.convert)
            }
            .subscribeOn(Schedulers.boundedElastic())
            .then(
                Mono.just(
                    ResponseEntity.ok("Success!")
                )
            )
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