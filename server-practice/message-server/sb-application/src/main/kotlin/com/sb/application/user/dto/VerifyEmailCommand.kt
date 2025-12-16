package com.sb.application.user.dto

import com.sb.domain.user.value.Email

data class VerifyEmailCommand(
    val email: String,
    val token: String,
) {
    val emailVo get() = Email.of(email)
}