package com.sb.application.user.ports.input.command

import com.sb.application.user.dto.RegisterWithGoogleCommand

interface RegisterWithGoogleUsecase {
    suspend fun register(command: RegisterWithGoogleCommand): Long
}