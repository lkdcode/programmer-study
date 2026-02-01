package com.sb.adapter.user.input.rest

import com.sb.adapter.user.input.rest.request.RegisterWithEmailRequest
import com.sb.adapter.user.input.rest.request.RequestEmailVerificationRequest
import com.sb.adapter.user.input.rest.request.VerifyEmailRequest
import com.sb.application.user.ports.input.command.RegisterWithEmailUsecase
import com.sb.application.user.ports.input.command.RequestEmailVerificationUsecase
import com.sb.application.user.ports.input.command.VerifyEmailUsecase
import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.success
import jakarta.validation.Valid
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
) {

    @PostMapping("/public/sign-up/email-verifications")
    suspend fun requestEmailVerification(
        @RequestBody @Valid request: RequestEmailVerificationRequest,
    ): ApiResponseEntity<Void> {
        requestEmailVerificationUsecase.request(request.convert)

        return success()
    }

    @PostMapping("/public/sign-up/email-verifications/verify")
    suspend fun verifyEmail(
        @RequestBody @Valid request: VerifyEmailRequest,
    ): ApiResponseEntity<Void> {
        verifyEmailUsecase.verify(request.convert)

        return success()
    }

    @PostMapping("/public/sign-up/email")
    suspend fun registerWithEmail(
        @RequestBody @Valid request: RegisterWithEmailRequest,
    ): ApiResponseEntity<Void> {
        registerWithEmailUsecase.register(request.convert)

        return success()
    }
}