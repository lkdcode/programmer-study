package com.sb.application.user.service.dsl

import com.sb.application.user.dto.OAuth2ProvisioningCommand
import com.sb.domain.user.value.Email

internal class OAuth2UserProvisioningDsl private constructor(
    private val command: OAuth2ProvisioningCommand
) {
    private var newUserFlag: Boolean = false

    suspend fun requireEmailNotRegistered(function: suspend (Email) -> Unit) =
        function(command.emailVo)

    suspend fun checkAlreadyRegister(function: suspend (Email) -> Boolean) {
        newUserFlag = function(command.emailVo)
    }

    suspend fun whenNewUser(block: suspend OAuth2ProvisioningCommand.() -> Unit) {
        if (newUserFlag) {
            block(command)
        }
    }

    suspend fun whenExistingUser(block: suspend (Email) -> Unit) {
        block(command.emailVo)
    }

    companion object {
        internal suspend fun execute(
            command: OAuth2ProvisioningCommand,
            block: suspend OAuth2UserProvisioningDsl.() -> Unit,
        ) = block(
            OAuth2UserProvisioningDsl(command)
        )
    }
}