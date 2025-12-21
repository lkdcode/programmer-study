package com.sb.application.auth.dto

import java.time.Instant

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpiresAt: Instant,
)