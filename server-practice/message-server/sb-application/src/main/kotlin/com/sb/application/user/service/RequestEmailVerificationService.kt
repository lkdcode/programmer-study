package com.sb.application.user.service

import com.sb.application.user.dto.RequestEmailVerificationCommand
import com.sb.application.user.guard.UserGuard
import com.sb.application.user.ports.input.command.RequestEmailVerificationUsecase
import com.sb.application.user.ports.output.cache.EmailVerificationPort
import com.sb.application.user.ports.output.external.EmailSenderPort
import com.sb.domain.user.spec.IdentityVerificationSpec
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RequestEmailVerificationService(
    private val emailVerificationPort: EmailVerificationPort,
    private val emailSenderPort: EmailSenderPort,
    private val userGuard: UserGuard,
    private val appScope: CoroutineScope,
) : RequestEmailVerificationUsecase {

    override suspend fun request(command: RequestEmailVerificationCommand) {
        userGuard.requireEmailNotRegistered(command.emailVo)

        appScope.launch {
            val token = IdentityVerificationSpec.generateToken()
            val signUpKey = IdentityVerificationSpec.generateSignUpKey(command.emailVo)

            runCatching {
                emailVerificationPort.save(signUpKey, token.value, IdentityVerificationSpec.defaultTTL())
                emailSenderPort.sendVerificationCode(command.emailVo, token)
            }.onFailure {
                emailVerificationPort.remove(signUpKey)
            }
        }
    }
}