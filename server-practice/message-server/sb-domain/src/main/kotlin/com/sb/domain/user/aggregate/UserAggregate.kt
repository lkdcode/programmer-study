package com.sb.domain.user.aggregate

import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.Password
import com.sb.domain.user.value.UserRole
import java.time.Instant

class UserAggregate private constructor(
    private var user: User
) {
    val getUser: User get() = user
    val getUserId: User.UserId get() = user.id

    val id: User.UserId get() = user.id
    val email: Email get() = user.email
    val nickname: Nickname get() = user.nickname
    val password: Password? get() = user.password
    val signUpType: User.SignUpType get() = user.signUpType
    val provider: String? get() = user.provider
    val providerUserId: String? get() = user.providerUserId
    val role: UserRole get() = user.role
    val createdAt: Instant get() = user.createdAt
    val updatedAt: Instant? get() = user.updatedAt


    companion object {
        fun from(user: User): UserAggregate = UserAggregate(user)
    }
}