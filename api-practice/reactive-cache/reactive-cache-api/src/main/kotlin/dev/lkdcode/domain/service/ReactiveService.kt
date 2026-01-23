package dev.lkdcode.domain.service

import dev.lkdcode.domain.repository.ReactiveRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReactiveService(
    private val reactiveRepository: ReactiveRepository
) {

    fun fetch(userId: Long): Mono<String> {
        println("ReactiveService.fetch USER_ID: $userId")

        return reactiveRepository.findByUserId(userId).map { "→ ReactiveService.fetch → $it" }
    }

    fun create(userId: Long): Mono<String> {
        println("ReactiveService.create USER_ID: $userId")

        return reactiveRepository.save(userId).map { "→ ReactiveService.create → $it" }
    }

    fun update(userId: Long): Mono<String> {
        println("ReactiveService.update USER_ID: $userId")

        return reactiveRepository.updateByUserId(userId).map { "→ ReactiveService.update → $it" }
    }

    fun delete(userId: Long): Mono<String> {
        println("ReactiveService.delete USER_ID: $userId")

        return reactiveRepository.deleteByUserId(userId).map { "→ ReactiveService.delete → $it" }
    }
}