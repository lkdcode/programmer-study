package com.sb.application.user.usecase

import com.sb.application.user.dto.RegisterWithEmailCommand
import com.sb.application.user.service.RegisterWithEmailService
import com.sb.domain.user.aggregate.UserAggregate
import com.sb.domain.user.spec.EmailVerificationSpec

class RegisterWithEmailDsl private constructor(
    private val command: RegisterWithEmailCommand,
) {
    val signUpKey get() = EmailVerificationSpec.generateSignUpKey(command.emailVo)

    fun requirePasswordConfirmed() {
        require(command.password == command.passwordConfirm) { PASSWORD_CONFIRM_MESSAGE }
    }

    suspend fun RegisterWithEmailService.requireEmailNotRegistered() {
        guardEmailNotRegistered(command.emailVo)
    }

    suspend fun RegisterWithEmailService.requireEmailVerified() {
        require(emailVerificationPort.isVerified(signUpKey)) { "이메일 인증이 필요합니다." }
    }

    suspend fun RegisterWithEmailService.register(): Long =
        userCommandPort.save(
            UserAggregate.registerWithEmail(
                email = command.signUpVo.email,
                nickname = command.signUpVo.nickname,
                password = command.signUpVo.password,
            )
        ).getUser.id.value

    suspend fun RegisterWithEmailService.consumeVerification() {
        emailVerificationPort.remove(signUpKey)
    }

    suspend fun RegisterWithEmailService.rewardSignupBonus(userId: Long) {
        rewardSignupBonusUsecase.reward(userId)
    }

    companion object {
        const val PASSWORD_CONFIRM_MESSAGE = "비밀번호가 일치하지 않습니다."

        suspend fun execute(
            command: RegisterWithEmailCommand,
            block: suspend RegisterWithEmailDsl.() -> Long,
        ): Long = block(
            RegisterWithEmailDsl(
                command = command,
            )
        )
    }
}