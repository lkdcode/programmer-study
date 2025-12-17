package com.sb.domain.user.value.signup

import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.Password

data class EmailSignUpCommand(
    val email: Email,
    val nickname: Nickname,
    val password: Password,
)