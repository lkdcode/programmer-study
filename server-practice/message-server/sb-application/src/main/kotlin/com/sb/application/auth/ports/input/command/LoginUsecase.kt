package com.sb.application.auth.ports.input.command

import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email

interface LoginUsecase {
    suspend fun onLoginSuccess(userId: User.UserId)
    suspend fun onLoginSuccess(email: Email)

    suspend fun onLoginFail(userId: User.UserId)
    suspend fun onLoginFail(email: Email)
}