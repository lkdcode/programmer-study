package com.sb.application.user.ports.input.command

import com.sb.application.user.dto.RegisterWithEmailCommand

interface RegisterWithEmailUsecase {
    suspend fun register(command: RegisterWithEmailCommand)
}