package com.sb.adapter.user.input.rest.request

import com.sb.application.user.dto.RequestEmailVerificationCommand
import com.sb.domain.user.value.Email as DomainEmail
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RequestEmailVerificationRequest(
    @field:Email(message = DomainEmail.INVALID_MESSAGE)
    @field:NotBlank(message = DomainEmail.REQUIRE_MESSAGE)
    val email: String,
) {
    val convert get() = RequestEmailVerificationCommand(email)
}