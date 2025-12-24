package com.sb.domain.user.aggregate

import com.sb.domain.user.entity.User
import java.time.Instant

class UserAggregate private constructor(
    private var user: User
) {
    val snapshot: User get() = user

    val isAccountLocked: Boolean get() = user.loginAttemptCount > MAX_LOGIN_ATTEMPTS

    fun succeedLogin(): UserAggregate {
        user = user.copy(loginAttemptCount = 0)
        return this
    }

    fun recordLoginSuccess(): UserAggregate {
        user = user.copy(lastLoginAt = Instant.now())
        return this
    }

    fun failLogin(): UserAggregate {
        user = user.copy(loginAttemptCount = user.loginAttemptCount + 1)
        return this
    }

    companion object {
        const val MAX_LOGIN_ATTEMPTS = 5

        fun from(user: User): UserAggregate = UserAggregate(user)
    }
}