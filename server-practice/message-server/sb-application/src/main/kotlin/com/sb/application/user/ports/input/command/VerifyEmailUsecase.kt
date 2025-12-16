package com.sb.application.user.ports.input.command

import com.sb.application.user.dto.VerifyEmailCommand

interface VerifyEmailUsecase {
    suspend fun verify(command: VerifyEmailCommand)
}