package com.sb.application.calligraphy.ports.output.checker

import com.sb.domain.calligraphy.policy.CreateCalligraphyPolicy
import com.sb.domain.calligraphy.value.Author

interface CalligraphyCreditChecker {
    suspend fun isEnoughCreditForCreate(author: Author): CreateCalligraphyPolicy.CreditSufficiency
}