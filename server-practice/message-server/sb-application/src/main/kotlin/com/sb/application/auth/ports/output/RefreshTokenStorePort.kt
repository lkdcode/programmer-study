package com.sb.application.auth.ports.output

import java.time.Instant

interface RefreshTokenStorePort {
    suspend fun save(userId: Long, refreshToken: String, expiredAt: Instant)
}