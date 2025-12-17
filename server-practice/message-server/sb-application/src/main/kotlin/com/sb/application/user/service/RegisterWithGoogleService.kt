package com.sb.application.user.service

import com.sb.application.user.dto.RegisterWithGoogleCommand
import com.sb.application.user.ports.input.command.RegisterWithGoogleUsecase
import com.sb.application.plan.ports.input.command.RewardSignupBonusUsecase
import com.sb.application.user.guard.UserGuard
import com.sb.application.user.ports.output.command.UserCommandPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterWithGoogleService(
    private val userGuard: UserGuard,
    private val userCommandPort: UserCommandPort,
    private val rewardSignupBonusUsecase: RewardSignupBonusUsecase,
) : RegisterWithGoogleUsecase {
    override suspend fun register(command: RegisterWithGoogleCommand): Long {
        userGuard.requireEmailNotRegistered(command.emailVo)

        val savedUserId = userCommandPort.save(command.newUser)

        rewardSignupBonusUsecase.reward(savedUserId)
        return savedUserId.value
    }
}