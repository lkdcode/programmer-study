package com.sb.application.user.ports.input.command

import com.sb.application.user.dto.RequestEmailVerificationCommand

interface RequestEmailVerificationUsecase {
    suspend fun request(command: RequestEmailVerificationCommand)
}