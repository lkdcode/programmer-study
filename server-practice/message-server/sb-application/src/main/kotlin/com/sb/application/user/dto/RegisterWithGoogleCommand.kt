package com.sb.application.user.dto

import com.sb.domain.user.value.Email
import com.sb.domain.user.value.signup.GoogleSignUpCommand
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.OAuthSubject
import com.sb.domain.user.model.NewUser

data class RegisterWithGoogleCommand(
    val email: String,
    val nickname: String,
    val oauthSubject: String,
) {
    val emailVo get(): Email = Email.of(email)
    val nicknameVo get(): Nickname = Nickname.of(nickname)
    val subjectVo get(): OAuthSubject = OAuthSubject.of(oauthSubject)

    val signUpVo get(): GoogleSignUpCommand = GoogleSignUpCommand(
        email = emailVo,
        nickname = nicknameVo,
        subject = subjectVo,
    )

    val newUser
        get() = NewUser.registerWithGoogle(
            email = emailVo,
            nickname = nicknameVo,
            subject = subjectVo,
        )
}