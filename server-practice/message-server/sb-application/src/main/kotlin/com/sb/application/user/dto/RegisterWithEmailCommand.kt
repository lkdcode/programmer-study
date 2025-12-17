package com.sb.application.user.dto

import com.sb.domain.user.model.NewUser
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.Password

data class RegisterWithEmailCommand(
    val email: String,
    val nickname: String,
    val password: String,
    val passwordConfirm: String,
) {
    val emailVo get(): Email = Email.of(email)
    val nicknameVo get(): Nickname = Nickname.of(nickname)
    val passwordVo get(): Password = Password.of(password)

    val newUser
        get() = NewUser.registerWithEmail(
            email = this.emailVo,
            nickname = this.nicknameVo,
            password = this.passwordVo,
        )
}