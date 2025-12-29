package com.sb.domain.user.model

import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.Password
import com.sb.domain.user.value.UserRole

data class NewUser(
    val email: Email,
    val nickname: Nickname,
    val password: Password?,
    val signUpType: User.SignUpType,
    val provider: String?,
    val providerUserId: String?,
    val profileImage: String?,
    val role: UserRole,
) {
    companion object {
        fun registerWithEmail(
            email: Email,
            nickname: Nickname,
            password: Password,
        ): NewUser = NewUser(
            email = email,
            nickname = nickname,
            password = password,
            signUpType = User.SignUpType.EMAIL,
            provider = null,
            providerUserId = null,
            profileImage = null,
            role = UserRole.USER,
        )

        fun registerWithGoogle(
            email: Email,
            nickname: Nickname,
            provider: String,
            providerUserId: String,
            profileImage: String? = null,
        ): NewUser = NewUser(
            email = email,
            nickname = nickname,
            password = null,
            signUpType = User.SignUpType.GOOGLE,
            provider = provider,
            providerUserId = providerUserId,
            profileImage = profileImage,
            role = UserRole.USER,
        )
    }
}