package com.sb.adapter.user.input.rest.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.sb.application.user.dto.VerifyEmailCommand
import com.sb.domain.user.value.Email as DomainEmail
import com.sb.domain.user.value.EmailVerificationToken
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class VerifyEmailRequest(
    @field:Email(message = DomainEmail.INVALID_MESSAGE)
    @field:NotBlank(message = DomainEmail.REQUIRE_MESSAGE)
    val email: String,

    @field:NotBlank(message = EmailVerificationToken.REQUIRE_MESSAGE)
    val token: String,
) {

    val convert
        get() = VerifyEmailCommand(
            email, token
        )
}