package com.sb.domain.user.entity

import com.sb.domain.user.value.Email
import com.sb.domain.user.value.EmailVerificationToken
import java.time.Instant

data class EmailVerification(
    val id: EmailVerificationId,
    val email: Email,
    val token: EmailVerificationToken,
    val status: Status,
    val expiresAt: Instant,
    val createdAt: Instant,
    val verifiedAt: Instant?,
) {
    @JvmInline
    value class EmailVerificationId(val value: Long)

    enum class Status {
        PENDING, VERIFIED, CONSUMED, EXPIRED
    }
}