package com.sb.adapter.auth.output.command

import com.sb.adapter.user.output.infrastructure.r2dbc.entity.MstRefreshTokenR2dbcEntity
import com.sb.adapter.user.output.infrastructure.r2dbc.repository.MstRefreshTokenR2dbcRepository
import com.sb.application.auth.ports.output.command.RefreshTokenStorePort
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class RefreshTokenStoreAdapter(
    private val repository: MstRefreshTokenR2dbcRepository,
) : RefreshTokenStorePort {

    override suspend fun save(userId: Long, refreshToken: String, expiredAt: Instant) {
        repository.save(
            MstRefreshTokenR2dbcEntity(
                userId = userId,
                jwt = refreshToken,
                expiredAt = expiredAt,
            )
        ).awaitSingle()
    }
}