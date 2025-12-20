package com.sb.application.user.ports.input.command

import com.sb.application.user.dto.OAuth2ProvisioningCommand

interface OAuth2UserProvisioningUsecase {
    suspend fun provision(command: OAuth2ProvisioningCommand)
}