package com.sb.domain.user.model

import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.OAuthSubject
import com.sb.domain.user.value.Password
import java.time.Instant

data class NewUser(
    val email: Email,
    val nickname: Nickname,
    val password: Password?,
    val signUpType: User.SignUpType,
    val oauthSubject: OAuthSubject?,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun registerWithEmail(
            email: Email,
            nickname: Nickname,
            password: Password,
            now: Instant = Instant.now(),
        ): NewUser = NewUser(
            email = email,
            nickname = nickname,
            password = password,
            signUpType = User.SignUpType.EMAIL,
            oauthSubject = null,
            createdAt = now,
            updatedAt = now,
        )

        fun registerWithGoogle(
            email: Email,
            nickname: Nickname,
            subject: OAuthSubject,
            now: Instant = Instant.now(),
        ): NewUser = NewUser(
            email = email,
            nickname = nickname,
            password = null,
            signUpType = User.SignUpType.GOOGLE,
            oauthSubject = subject,
            createdAt = now,
            updatedAt = now,
        )
    }
}