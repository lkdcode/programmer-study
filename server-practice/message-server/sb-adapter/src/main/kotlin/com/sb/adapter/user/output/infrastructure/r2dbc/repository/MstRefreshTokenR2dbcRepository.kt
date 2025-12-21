package com.sb.adapter.user.output.infrastructure.r2dbc.repository

import com.sb.adapter.user.output.infrastructure.r2dbc.entity.MstRefreshTokenR2dbcEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface MstRefreshTokenR2dbcRepository : R2dbcRepository<MstRefreshTokenR2dbcEntity, Long> {
    fun findByUserId(userId: Long): Mono<MstRefreshTokenR2dbcEntity>
    fun findByJwt(jwt: String): Mono<MstRefreshTokenR2dbcEntity>
}