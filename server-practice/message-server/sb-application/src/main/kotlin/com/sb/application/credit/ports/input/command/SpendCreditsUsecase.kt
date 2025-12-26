package com.sb.application.credit.ports.input.command

import com.sb.application.credit.dto.SpendCreditsCommand

interface SpendCreditsUsecase {
    suspend fun spend(command: SpendCreditsCommand)
}