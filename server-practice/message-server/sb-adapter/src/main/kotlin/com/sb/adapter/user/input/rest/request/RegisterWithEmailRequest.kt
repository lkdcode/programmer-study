package com.sb.adapter.user.input.rest.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.sb.application.user.dto.RegisterWithEmailCommand
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.Password
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import com.sb.domain.user.value.Email as DomainEmail

data class RegisterWithEmailRequest(
    @field:Email(message = DomainEmail.INVALID_MESSAGE)
    @field:NotBlank(message = DomainEmail.REQUIRE_MESSAGE)
    val email: String,

    @field:NotBlank(message = Nickname.REQUIRE_MESSAGE)
    @field:Size(
        min = Nickname.MIN_LENGTH,
        max = Nickname.MAX_LENGTH,
        message = Nickname.INVALID_LENGTH_MESSAGE
    )
    val nickname: String,

    @field:NotBlank(message = Password.REQUIRE_MESSAGE)
    @field:Size(
        min = Password.MIN_LENGTH,
        max = Password.MAX_LENGTH,
        message = Password.INVALID_LENGTH_MESSAGE
    )
    val password: String,

    @field:NotBlank(message = Password.REQUIRE_MESSAGE)
    @field:Size(
        min = Password.MIN_LENGTH,
        max = Password.MAX_LENGTH,
        message = Password.INVALID_LENGTH_MESSAGE
    )
    val passwordConfirm: String,
) {

    @get:JsonIgnore
    val convert
        get() = RegisterWithEmailCommand(
            email = this.email,
            nickname = this.nickname,
            password = this.password,
            passwordConfirm = this.passwordConfirm,
        )
}