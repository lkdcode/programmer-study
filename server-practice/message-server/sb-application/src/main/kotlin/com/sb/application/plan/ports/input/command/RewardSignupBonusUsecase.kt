package com.sb.application.plan.ports.input.command

import com.sb.domain.user.entity.User

interface RewardSignupBonusUsecase {
    suspend fun reward(userId: User.UserId)
}