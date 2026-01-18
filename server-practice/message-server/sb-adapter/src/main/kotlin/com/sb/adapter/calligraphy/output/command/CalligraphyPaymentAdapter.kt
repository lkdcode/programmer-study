package com.sb.adapter.calligraphy.output.command

import com.sb.application.calligraphy.ports.output.command.CalligraphyPaymentPort
import com.sb.application.credit.dto.SpendCreditsCommand
import com.sb.application.credit.ports.input.command.SpendCreditsUsecase
import com.sb.domain.calligraphy.policy.CreateCalligraphyPolicy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.credit.value.Credit
import com.sb.domain.credit.value.TransactionReason
import com.sb.domain.credit.value.TransactionType
import org.springframework.stereotype.Component


@Component
class CalligraphyPaymentAdapter(
    private val spendCreditsUsecase: SpendCreditsUsecase
) : CalligraphyPaymentPort {

    override suspend fun payForCreate(author: Author) =
        spendCreditsUsecase.spend(
            SpendCreditsCommand(
                userId = author.userId.value,
                amount = Credit.of(CreateCalligraphyPolicy.CREATION_COST),
                type = TransactionType.DEBIT,
                reason = TransactionReason.CALLIGRAPHY_CREATE,
            )
        )
}