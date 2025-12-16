package com.sb.adapter.user.input.rest

import com.sb.adapter.user.input.rest.request.RegisterWithEmailRequest
import com.sb.adapter.user.input.rest.request.RegisterWithGoogleRequest
import com.sb.adapter.user.input.rest.request.RequestEmailVerificationRequest
import com.sb.adapter.user.input.rest.request.VerifyEmailRequest
import com.sb.adapter.user.input.rest.response.RegisterResponse
import com.sb.application.user.ports.input.command.RegisterWithEmailUsecase
import com.sb.application.user.ports.input.command.RegisterWithGoogleUsecase
import com.sb.application.user.ports.input.command.RequestEmailVerificationUsecase
import com.sb.application.user.ports.input.command.VerifyEmailUsecase
import com.sb.framework.api.ApiEntity
import com.sb.framework.api.toApiResponseEntity
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
class UserRegistrationApi(
    private val requestEmailVerificationUsecase: RequestEmailVerificationUsecase,
    private val verifyEmailUsecase: VerifyEmailUsecase,
    private val registerWithEmailUsecase: RegisterWithEmailUsecase,
    private val registerWithGoogleUsecase: RegisterWithGoogleUsecase,
) {

    @PostMapping("/api/users/email-verifications")
    suspend fun requestEmailVerification(
        @RequestBody @Valid request: RequestEmailVerificationRequest,
    ): ApiEntity<Unit> {
        requestEmailVerificationUsecase.request(request.convert)

        return Unit.toApiResponseEntity(status = HttpStatus.CREATED, message = "OK")
    }

    @PostMapping("/api/users/email-verifications/verify")
    suspend fun verifyEmail(
        @RequestBody @Valid request: VerifyEmailRequest,
    ): ApiEntity<Unit> {
        verifyEmailUsecase.verify(request.convert)

        return Unit.toApiResponseEntity(status = HttpStatus.OK, message = "OK")
    }

    @PostMapping("/api/users/register/email")
    suspend fun registerWithEmail(
        @RequestBody @Valid request: RegisterWithEmailRequest,
    ): ApiEntity<RegisterResponse> {
        val userId = registerWithEmailUsecase.register(request.convert)

        return RegisterResponse(userId).toApiResponseEntity(status = HttpStatus.CREATED, message = "OK")
    }

    @PostMapping("/api/users/register/google")
    suspend fun registerWithGoogle(
        @RequestBody @Valid request: RegisterWithGoogleRequest,
    ): ApiEntity<RegisterResponse> {
        val userId = registerWithGoogleUsecase.register(request.convert)

        return RegisterResponse(userId).toApiResponseEntity(status = HttpStatus.CREATED, message = "OK")
    }
}