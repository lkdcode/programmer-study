package dev.lkdcode.infrastructure.repository

import dev.lkdcode.infrastructure.entity.Kiwi
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface KiwiRepository : R2dbcRepository<Kiwi, Long> {
    fun deleteAllByName(name: String): Mono<Void>
}