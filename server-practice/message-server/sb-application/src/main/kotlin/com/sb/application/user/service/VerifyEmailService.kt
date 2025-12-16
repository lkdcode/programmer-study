package com.sb.application.user.service

import com.sb.application.user.dto.VerifyEmailCommand
import com.sb.application.user.ports.input.command.VerifyEmailUsecase
import com.sb.application.user.ports.output.cache.EmailVerificationPort
import com.sb.domain.user.spec.EmailVerificationSpec
import com.sb.domain.user.value.EmailVerificationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VerifyEmailService(
    private val emailVerificationPort: EmailVerificationPort,
) : VerifyEmailUsecase {

    override suspend fun verify(command: VerifyEmailCommand) {
        val signUpKey = EmailVerificationSpec.generateSignUpKey(command.emailVo)
        emailVerificationPort.validate(
            signUpKey,
            EmailVerificationToken.of(command.token)
        )

        emailVerificationPort.save(
            signUpKey,
            EmailVerificationSpec.VERIFICATION_COMPLETED,
            EmailVerificationSpec.defaultTTL()
        )
    }
}