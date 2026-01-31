package com.sb.adapter.plan.output.infrastructure.r2dbc.repository

import com.sb.adapter.plan.output.infrastructure.r2dbc.entity.PaymentOrderR2dbcEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux

interface PaymentOrderR2dbcRepository : R2dbcRepository<PaymentOrderR2dbcEntity, Long> {
    fun findAllByUserId(userId: Long): Flux<PaymentOrderR2dbcEntity>
}