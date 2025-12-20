package com.sb.application.user.dto

import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.model.NewUser

data class OAuth2ProvisioningCommand(
    val email: String,
    val nickname: String,
    val provider: String,
    val providerUserId: String,
    val profileImage: String?,
) {
    val emailVo get(): Email = Email.of(email)
    val nicknameVo get(): Nickname = Nickname.of(nickname)

    val newUser
        get() = NewUser.registerWithGoogle(
            email = emailVo,
            nickname = nicknameVo,
            provider = provider,
            providerUserId = providerUserId,
            profileImage = profileImage,
        )
}