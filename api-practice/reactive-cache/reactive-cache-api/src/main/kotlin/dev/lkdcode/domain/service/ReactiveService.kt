package dev.lkdcode.domain.service

import dev.lkdcode.cache.annotation.ReactiveCacheEvict
import dev.lkdcode.cache.annotation.ReactiveCachePut
import dev.lkdcode.cache.annotation.ReactiveCacheable
import dev.lkdcode.domain.repository.ReactiveRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReactiveService(
    private val reactiveRepository: ReactiveRepository
) {

    @ReactiveCacheable(
        key = "'user:' + #userId",
        ttl = 300,
        condition = "#userId > 0",
        unless = "#result == null"
    )
    fun fetch(userId: Long): Mono<String> {
        println("ReactiveService.fetch USER_ID: $userId")

        return reactiveRepository.findByUserId(userId).map { "→ ReactiveService.fetch → $it" }
    }

    @ReactiveCachePut(
        key = "'user:' + #userId",
        ttl = 300
    )
    fun create(userId: Long): Mono<String> {
        println("ReactiveService.create USER_ID: $userId")

        return reactiveRepository.save(userId).map { "→ ReactiveService.create → $it" }
    }

    @ReactiveCachePut(
        key = "'user:' + #userId",
        ttl = 300
    )
    fun update(userId: Long): Mono<String> {
        println("ReactiveService.update USER_ID: $userId")

        return reactiveRepository.updateByUserId(userId).map { "→ ReactiveService.update → $it" }
    }

    @ReactiveCacheEvict(key = "'user:' + #userId")
    fun delete(userId: Long): Mono<String> {
        println("ReactiveService.delete USER_ID: $userId")

        return reactiveRepository.deleteByUserId(userId).map { "→ ReactiveService.delete → $it" }
    }
}