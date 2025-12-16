package com.sb.application.user.service

import com.sb.application.plan.ports.input.command.RewardSignupBonusUsecase
import com.sb.application.user.guard.UserGuard
import com.sb.application.user.dto.RegisterWithEmailCommand
import com.sb.application.user.ports.input.command.RegisterWithEmailUsecase
import com.sb.application.user.ports.output.cache.EmailVerificationPort
import com.sb.application.user.ports.output.command.UserCommandPort
import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.application.user.usecase.RegisterWithEmailDsl
import com.sb.domain.user.value.Email
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterWithEmailService(
    private val userGuard: UserGuard,
    val userQueryPort: UserQueryPort,
    val userCommandPort: UserCommandPort,
    val emailVerificationPort: EmailVerificationPort,
    val rewardSignupBonusUsecase: RewardSignupBonusUsecase,
) : RegisterWithEmailUsecase {

    internal suspend fun guardEmailNotRegistered(email: Email) {
        userGuard.requireEmailNotRegistered(email)
    }

    override suspend fun register(command: RegisterWithEmailCommand): Long =
        RegisterWithEmailDsl.execute(command) {
            requirePasswordConfirmed()
            requireEmailNotRegistered()
            requireEmailVerified()

            val userId = register()
            consumeVerification()
            rewardSignupBonus(userId)
            userId
        }
}