package com.sb.application.credit.service.dsl

import com.sb.application.credit.dto.SpendCreditsCommand

internal class SpendCreditsServiceDsl private constructor(
    val command: SpendCreditsCommand
) {

    internal suspend fun executePay(
        block: suspend SpendCreditsCommand.() -> Unit
    ) {
        block(command)
    }

    internal suspend fun executeTx(
        block: suspend SpendCreditsCommand.() -> Unit
    ) {
        block(command)
    }

    companion object {
        internal suspend fun execute(
            command: SpendCreditsCommand,
            block: suspend SpendCreditsServiceDsl.() -> Unit
        ) = block(SpendCreditsServiceDsl(command))
    }
}