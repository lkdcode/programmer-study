package dev.lkdcode.api

import dev.lkdcode.infrastructure.entity.Kiwi
import dev.lkdcode.service.KiwiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class KiwiApi(
    private val kiwiService: KiwiService,
) {
    @GetMapping("/api/kiwi")
    fun fetch(): MonoResponseEntity<List<Kiwi>> =
        kiwiService
            .fetch()
            .map { response(it) }

    @PostMapping("/api/kiwi")
    fun insert(): MonoResponseEntity<String> =
        kiwiService
            .insert()
            .thenReturn(response("OK"))

    @DeleteMapping("/api/kiwi/{name}")
    fun delete(@PathVariable(name = "name") name: String): MonoResponseEntity<List<Kiwi>> =
        kiwiService
            .deleteAndFetch(name)
            .map { response(it) }
}

typealias MonoResponseEntity<T> = Mono<ResponseEntity<T>>

fun <T> response(payload: T) = ResponseEntity.ok().body(payload)