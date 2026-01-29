package dev.lkdcode.domain.api

import dev.lkdcode.domain.service.PostService
import dev.lkdcode.domain.value.Post
import org.springframework.web.bind.annotation.*
import reactor.core.scheduler.Schedulers

@RestController
class PostApi(
    private val postService: PostService,
) {

    @GetMapping("/posts/{postId}")
    fun fetch(@PathVariable(name = "postId") postId: Long): MonoResponseEntity<Post> =
//        postService
//            .fetch(postId)
//            .map { monoResponseEntity(it) }
        MonoResponseEntity.fromCallable { postService.fetch(postId) }
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap { it }
            .map { monoResponseEntity(it) }

    @PostMapping("/posts/{postId}")
    fun create(@PathVariable(name = "postId") postId: Long): MonoResponseEntity<Post> =
        postService
            .create(postId)
            .map { monoResponseEntity(it) }

    @PutMapping("/posts/{postId}")
    fun update(@PathVariable(name = "postId") postId: Long): MonoResponseEntity<Post> =
        postService
            .update(postId)
            .map { monoResponseEntity(it) }

    @DeleteMapping("/posts/{postId}")
    fun delete(@PathVariable(name = "postId") postId: Long): MonoResponseEntity<Post> =
        postService
            .delete(postId)
            .map { monoResponseEntity(it) }
}