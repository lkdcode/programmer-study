package com.sb.application.credit.ports.input.command

import com.sb.domain.user.entity.User

interface RewardLoginBonusUsecase {
    suspend fun reward(userId: User.UserId)
}