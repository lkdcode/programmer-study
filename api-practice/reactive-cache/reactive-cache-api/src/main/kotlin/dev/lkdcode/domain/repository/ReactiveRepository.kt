package dev.lkdcode.domain.repository

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ReactiveRepository {
    fun findByUserId(userId: Long): Mono<String> {
        println("ReactiveRepository.findByUserId USER_ID: $userId")

        return Mono.just("ReactiveRepository.findByUserId. USER_ID: $userId")
    }

    fun save(userId: Long): Mono<String> {
        println("ReactiveRepository.save USER_ID: $userId")

        return Mono.just("ReactiveRepository.save. USER_ID: $userId")
    }

    fun updateByUserId(userId: Long): Mono<String> {
        println("ReactiveRepository.updateByUserId USER_ID: $userId")

        return Mono.just("ReactiveRepository.updateByUserId. USER_ID: $userId")
    }

    fun deleteByUserId(userId: Long): Mono<String> {
        println("ReactiveRepository.deleteByUserId USER_ID: $userId")

        return Mono.just("ReactiveRepository.deleteByUserId. USER_ID: $userId")
    }
}