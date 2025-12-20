package com.sb.application.user.service

import com.sb.application.plan.ports.input.command.RewardSignupBonusUsecase
import com.sb.application.user.dto.OAuth2ProvisioningCommand
import com.sb.application.user.guard.UserGuard
import com.sb.application.user.ports.input.command.OAuth2UserProvisioningUsecase
import com.sb.application.user.ports.output.command.UserCommandPort
import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.application.user.usecase.OAuth2UserProvisioningDsl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class OAuth2UserProvisioningService(
    private val userGuard: UserGuard,
    private val userQueryPort: UserQueryPort,
    private val userCommandPort: UserCommandPort,
    private val rewardSignupBonusUsecase: RewardSignupBonusUsecase,
) : OAuth2UserProvisioningUsecase {

    override suspend fun provision(command: OAuth2ProvisioningCommand) {
        OAuth2UserProvisioningDsl.execute(command) {
            checkAlreadyRegister(userQueryPort::alreadyRegisterByEmail)

            whenNewUser {
                val userId = userCommandPort.save(command.newUser)
                rewardSignupBonusUsecase.reward(userId)
            }

            whenExistingUser (userCommandPort::recordLoginAt)
        }
    }
}