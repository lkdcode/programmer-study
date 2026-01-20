package com.sb.adapter.archive.output.infrastructure.r2dbc.repository

import com.sb.adapter.archive.output.infrastructure.r2dbc.entity.CalligraphyArchiveR2dbcEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface CalligraphyArchiveR2dbcRepository : R2dbcRepository<CalligraphyArchiveR2dbcEntity, Long> {
    fun existsByCalligraphyIdAndUserId(calligraphyId: UUID, userId: Long): Mono<Boolean>
    fun findAllByUserId(userId: Long): Flux<CalligraphyArchiveR2dbcEntity>
    fun countByCalligraphyId(calligraphyId: UUID): Mono<Long>
    fun deleteByCalligraphyIdAndUserId(calligraphyId: UUID, userId: Long): Mono<Void>
}