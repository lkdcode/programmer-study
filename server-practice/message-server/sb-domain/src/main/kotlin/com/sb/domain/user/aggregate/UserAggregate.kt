package com.sb.domain.user.aggregate

import com.sb.domain.user.entity.User

class UserAggregate private constructor(
    private var user: User
) {
    val snapshot: User get() = user

    val loginAttemptCount: Int get() = user.loginAttemptCount
    val isAccountLocked: Boolean get() = user.loginAttemptCount > MAX_LOGIN_ATTEMPTS

    fun failLogin(): UserAggregate {
        user = user.copy(loginAttemptCount = user.loginAttemptCount + 1)
        return this
    }

    fun succeedLogin(): UserAggregate {
        user = user.copy(loginAttemptCount = 0)
        return this
    }

    companion object {
        const val MAX_LOGIN_ATTEMPTS = 5

        fun from(user: User): UserAggregate = UserAggregate(user)
    }
}