package com.sb.domain.user.value.signup

import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.OAuthSubject

data class GoogleSignUpCommand(
    val email: Email,
    val nickname: Nickname,
    val subject: OAuthSubject,
)