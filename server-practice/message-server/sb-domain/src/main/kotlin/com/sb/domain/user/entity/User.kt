package com.sb.domain.user.entity

import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.Password
import com.sb.domain.user.value.UserRole
import java.time.Instant

data class User(
    val id: UserId,
    val email: Email,
    val nickname: Nickname,
    val password: Password?,
    val signUpType: SignUpType,
    val provider: String?,
    val providerUserId: String?,
    val role: UserRole,
    val loginAttemptCount: Int = 0,
    val createdAt: Instant,
    val updatedAt: Instant?,
) {
    @JvmInline
    value class UserId(val value: Long)

    enum class SignUpType {
        EMAIL, GOOGLE
    }
}