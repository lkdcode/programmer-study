package com.sb.application.calligraphy.service.dsl

import com.sb.application.calligraphy.service.CreateCalligraphyService
import com.sb.domain.calligraphy.command.CreateCalligraphyCommand
import com.sb.domain.calligraphy.entity.Calligraphy.CalligraphyId
import com.sb.domain.calligraphy.policy.CreateCalligraphyPolicy

internal class CreateCalligraphyDsl private constructor(
    private val command: CreateCalligraphyCommand
) {
    internal fun CreateCalligraphyService.checkCreditSufficiency(): CreateCalligraphyPolicy.CreditSufficiency =
        calligraphyCreditChecker.isEnoughCreditForCreate(command.author)

    internal suspend fun CreateCalligraphyService.validateCanCreate(
        sufficiency: suspend CreateCalligraphyService.() -> CreateCalligraphyPolicy.CreditSufficiency
    ) =
        CreateCalligraphyPolicy.validateCanCreate(command.author, sufficiency())

    internal suspend fun CreateCalligraphyService.pay() =
        calligraphyPaymentPort.payForCreate(command.author)

    internal suspend fun CreateCalligraphyService.save(): CalligraphyId =
        commandPort.save(command).snapshot.id

    companion object {
        internal suspend fun <T> execute(
            command: CreateCalligraphyCommand,
            block: suspend CreateCalligraphyDsl.() -> T
        ): T = block(CreateCalligraphyDsl(command))
    }
}