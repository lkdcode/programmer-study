package com.sb.adapter.user.input.rest.request

import com.sb.application.user.dto.RegisterWithGoogleCommand
import com.sb.domain.user.value.Email as DomainEmail
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.OAuthSubject
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterWithGoogleRequest(
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

    @field:NotBlank(message = OAuthSubject.REQUIRE_MESSAGE)
    @field:Size(max = OAuthSubject.MAX_LENGTH, message = OAuthSubject.INVALID_LENGTH_MESSAGE)
    val oauthSubject: String,
) {
    val convert
        get() = RegisterWithGoogleCommand(
            email = this.email,
            nickname = this.nickname,
            oauthSubject = this.oauthSubject,
        )
}