package com.sb.application.user.dto

import com.sb.domain.user.value.Email

data class RequestEmailVerificationCommand(
    val email: String,
) {
    val emailVo get() = Email.of(email)
}