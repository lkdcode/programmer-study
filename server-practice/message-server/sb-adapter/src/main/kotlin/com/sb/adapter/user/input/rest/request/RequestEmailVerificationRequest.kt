package com.sb.adapter.user.input.rest.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.sb.application.user.dto.RequestEmailVerificationCommand
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import com.sb.domain.user.value.Email as DomainEmail

data class RequestEmailVerificationRequest(
    @field:Email(message = DomainEmail.INVALID_MESSAGE)
    @field:NotBlank(message = DomainEmail.REQUIRE_MESSAGE)
    val email: String,
) {

    @get:JsonIgnore
    val convert get() = RequestEmailVerificationCommand(email)
}