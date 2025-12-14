package com.sb.domain.user.aggregate

import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.OAuthSubject
import com.sb.domain.user.value.Password
import java.time.Instant

class UserAggregate private constructor(
    private var user: User
) {
    val getUser: User get() = user

    companion object {
        fun restore(user: User): UserAggregate = UserAggregate(user)

        fun registerWithEmail(
            email: Email,
            nickname: Nickname,
            password: Password,
            now: Instant = Instant.now(),
        ): UserAggregate = UserAggregate(
            User(
                id = generateUserId(),
                email = email,
                nickname = nickname,
                password = password,
                signUpType = User.SignUpType.EMAIL,
                emailVerified = true,
                oauthSubject = null,
                createdAt = now,
                updatedAt = now,
            )
        )

        fun registerWithGoogle(
            email: Email,
            nickname: Nickname,
            subject: OAuthSubject,
            now: Instant = Instant.now(),
        ): UserAggregate = UserAggregate(
            User(
                id = generateUserId(),
                email = email,
                nickname = nickname,
                password = null,
                signUpType = User.SignUpType.GOOGLE,
                emailVerified = true,
                oauthSubject = subject,
                createdAt = now,
                updatedAt = now,
            )
        )

        private fun generateUserId(): User.UserId =
            User.UserId(Instant.now().toEpochMilli())
    }
}