package com.sb.adapter.calligraphy.output.checker

import com.sb.application.calligraphy.ports.output.checker.CalligraphyCreditChecker
import com.sb.application.plan.ports.output.CreditChecker
import com.sb.domain.calligraphy.policy.CreateCalligraphyPolicy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.plan.value.Credit
import org.springframework.stereotype.Component


@Component
internal class CalligraphyCreditCheckAdapter(
    private val creditChecker: CreditChecker,
) : CalligraphyCreditChecker {

    override suspend fun isEnoughCreditForCreate(author: Author): CreateCalligraphyPolicy.CreditSufficiency {
        val isEnough = creditChecker.isEnoughByUserId(
            author.userId.value,
            CreateCalligraphyPolicy.CREATION_COST.credit()
        )

        return CreateCalligraphyPolicy.CreditSufficiency.convert(isEnough)
    }

    companion object {
        private fun Long.credit(): Credit = Credit.of(this)
    }
}