package com.sb.adapter.user.output.infrastructure.r2dbc.repository

import com.sb.adapter.user.output.infrastructure.r2dbc.entity.HisLoginR2dbcEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux

interface HisLoginR2dbcRepository : R2dbcRepository<HisLoginR2dbcEntity, Long> {
    fun findByUserId(userId: Long): Flux<HisLoginR2dbcEntity>
}