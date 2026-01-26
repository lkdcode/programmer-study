package dev.lkdcode.domain.api

import dev.lkdcode.domain.service.ReactiveService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class ReactiveApi(
    private val reactiveService: ReactiveService,
) {

    @GetMapping("/reactive/{userId}")
    fun fetch(@PathVariable(name = "userId") userId: Long): MonoResponseEntity<String> =
        reactiveService
            .fetch(userId)
            .map { monoResponseEntity(it) }

    @PostMapping("/reactive/{userId}")
    fun create(@PathVariable(name = "userId") userId: Long): MonoResponseEntity<String> =
        reactiveService
            .create(userId)
            .map { monoResponseEntity(it) }

    @PutMapping("/reactive/{userId}")
    fun update(@PathVariable(name = "userId") userId: Long): MonoResponseEntity<String> =
        reactiveService
            .update(userId)
            .map { monoResponseEntity(it) }

    @DeleteMapping("/reactive/{userId}")
    fun delete(@PathVariable(name = "userId") userId: Long): MonoResponseEntity<String> =
        reactiveService
            .delete(userId)
            .map { monoResponseEntity(it) }
}

typealias MonoResponseEntity<T> = Mono<ResponseEntity<T>>

fun <T> monoResponseEntity(payload: T) = ResponseEntity.ok().body(payload)