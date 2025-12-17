package com.sb.application.user.service

import com.sb.application.user.dto.VerifyEmailCommand
import com.sb.application.user.ports.input.command.VerifyEmailUsecase
import com.sb.application.user.ports.output.cache.EmailVerificationPort
import com.sb.domain.user.spec.IdentityVerificationSpec
import com.sb.domain.user.value.IdentityVerificationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VerifyEmailService(
    private val emailVerificationPort: EmailVerificationPort,
) : VerifyEmailUsecase {

    override suspend fun verify(command: VerifyEmailCommand) {
        val signUpKey = IdentityVerificationSpec.generateSignUpKey(command.emailVo)
        emailVerificationPort.validate(
            signUpKey,
            IdentityVerificationToken.of(command.token)
        )

        emailVerificationPort.save(
            signUpKey,
            IdentityVerificationSpec.SIGN_UP_KEY_VERIFICATION_COMPLETED,
            IdentityVerificationSpec.defaultTTL()
        )
    }
}