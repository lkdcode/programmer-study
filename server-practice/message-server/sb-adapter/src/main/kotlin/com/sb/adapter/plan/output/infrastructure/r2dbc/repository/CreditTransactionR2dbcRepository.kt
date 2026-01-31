package com.sb.adapter.plan.output.infrastructure.r2dbc.repository

import com.sb.adapter.plan.output.infrastructure.r2dbc.entity.CreditTransactionR2dbcEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono
import java.time.Instant

interface CreditTransactionR2dbcRepository : R2dbcRepository<CreditTransactionR2dbcEntity, Long> {
    fun existsByUserIdAndReasonAndCreatedAtBetween(
        userId: Long,
        reason: String,
        startAt: Instant,
        endAt: Instant,
    ): Mono<Boolean>
}