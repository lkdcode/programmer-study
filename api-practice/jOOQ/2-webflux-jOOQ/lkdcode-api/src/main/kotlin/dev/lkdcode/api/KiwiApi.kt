package dev.lkdcode.api

import dev.lkdcode.infrastructure.entity.Kiwi
import dev.lkdcode.service.KiwiService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class KiwiApi(
    private val kiwiService: KiwiService,
) {

    @PostMapping("/api/kiwi")
    fun insert(): Mono<String> {
        return kiwiService.insert().thenReturn("OK")
    }

    @DeleteMapping("/api/kiwi/{name}")
    fun delete(@PathVariable(name = "name") name: String): Mono<List<Kiwi>> {
        return kiwiService.deleteAndFetch(name)
    }
}