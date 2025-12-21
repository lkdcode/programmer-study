package com.sb.application.auth.ports.output.command

import java.time.Instant

interface RefreshTokenStorePort {
    suspend fun save(userId: Long, refreshToken: String, expiredAt: Instant)
}