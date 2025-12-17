package com.sb.application.user.ports.output.cache

import com.sb.domain.user.value.IdentityVerificationToken
import java.time.Duration

interface EmailVerificationPort {
    suspend fun save(signUpKey: String, token: String, ttl: Duration)
    suspend fun validate(signUpKey: String, token: IdentityVerificationToken)
    suspend fun isVerified(signUpKey: String): Boolean
    suspend fun remove(signUpKey: String)
}