package com.sb.application.user.service.dsl

import com.sb.application.common.validator.throwUnless
import com.sb.application.user.dto.RegisterWithEmailCommand
import com.sb.application.user.service.RegisterWithEmailService
import com.sb.domain.user.entity.User
import com.sb.domain.user.exception.UserErrorCode
import com.sb.domain.user.spec.IdentityVerificationSpec

internal class RegisterWithEmailDsl private constructor(
    private val command: RegisterWithEmailCommand,
) {
    internal val signUpKey get() = IdentityVerificationSpec.generateSignUpKey(command.emailVo)

    internal fun requirePasswordConfirmed() =
        throwUnless(
            command.password == command.passwordConfirm,
            UserErrorCode.PASSWORD_CONFIRM_MISMATCH
        )

    internal suspend fun RegisterWithEmailService.requireEmailNotRegistered() =
        guardEmailNotRegistered(command.emailVo)

    internal suspend fun RegisterWithEmailService.requireEmailVerified() =
        throwUnless(
            emailVerificationPort.isVerified(signUpKey),
            UserErrorCode.EMAIL_VERIFICATION_NOT_VERIFIED
        )

    internal suspend fun RegisterWithEmailService.register(): User.UserId =
        userCommandPort.save(command.newUser)

    internal suspend fun RegisterWithEmailService.consumeVerification() =
        emailVerificationPort.remove(signUpKey)

    internal suspend fun RegisterWithEmailService.createWallet(userId: User.UserId) =
        createWalletUsecase.create(userId)

    companion object {
        internal suspend fun execute(
            command: RegisterWithEmailCommand,
            block: suspend RegisterWithEmailDsl.() -> Unit,
        ) = block(
            RegisterWithEmailDsl(command)
        )
    }
}