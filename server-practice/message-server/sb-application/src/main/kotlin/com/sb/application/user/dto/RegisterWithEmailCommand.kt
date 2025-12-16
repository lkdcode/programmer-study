package com.sb.application.user.dto

import com.sb.domain.user.value.Email
import com.sb.domain.user.value.signup.EmailSignUpCommand
import com.sb.domain.user.value.EmailVerificationToken
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.Password

data class RegisterWithEmailCommand(
    val email: String,
    val nickname: String,
    val password: String,
    val passwordConfirm: String,
    val verificationToken: String,
) {
    val emailVo get(): Email = Email.of(email)
    val nicknameVo get(): Nickname = Nickname.of(nickname)
    val passwordVo get(): Password = Password.of(password)
    val verificationTokenVo get(): EmailVerificationToken = EmailVerificationToken.of(verificationToken)

    val signUpVo get(): EmailSignUpCommand = EmailSignUpCommand(
        email = emailVo,
        nickname = nicknameVo,
        password = passwordVo,
        verificationToken = verificationTokenVo,
    )
}