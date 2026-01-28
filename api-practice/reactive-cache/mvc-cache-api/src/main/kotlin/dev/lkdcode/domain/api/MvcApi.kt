package dev.lkdcode.domain.api

import dev.lkdcode.domain.service.MvcService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MvcApi(
    private val mvcService: MvcService,
) {

    @GetMapping("/mvc/{userId}")
    fun fetch(
        @PathVariable(name = "userId") userId: Long
    ): ResponseEntity<String> {
        val result = mvcService.fetch(userId)

        return ResponseEntity.ok().body(result)
    }

    @PostMapping("/mvc/{userId}")
    fun create(@PathVariable(name = "userId") userId: Long): ResponseEntity<String> {
        val result = mvcService.create(userId)

        return ResponseEntity.ok().body(result)
    }

    @PutMapping("/mvc/{userId}")
    fun update(@PathVariable(name = "userId") userId: Long): ResponseEntity<String> {
        val result = mvcService.update(userId)

        return ResponseEntity.ok().body(result)
    }

    @DeleteMapping("/mvc/{userId}")
    fun delete(@PathVariable(name = "userId") userId: Long): ResponseEntity<String> {
        val result = mvcService.delete(userId)

        return ResponseEntity.ok().body(result)
    }
}