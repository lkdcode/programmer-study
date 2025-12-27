package com.sb.domain.calligraphy.policy

import com.sb.domain.calligraphy.exception.CalligraphyErrorCode.INSUFFICIENT_CREDIT
import com.sb.domain.exception.domainRequire

class CreateCalligraphyPolicy {

    enum class CreditSufficiency {
        SUFFICIENT,
        INSUFFICIENT;

        val isSufficient: Boolean
            get() = this == SUFFICIENT

        companion object {
            fun convert(isEnough: Boolean) = if (isEnough) SUFFICIENT else INSUFFICIENT
        }
    }

    companion object {
        const val CREATION_COST = 200L

        fun validateCanCreate(canAfford: CreditSufficiency) {
            domainRequire(canAfford.isSufficient, INSUFFICIENT_CREDIT) { INSUFFICIENT_CREDIT.message }
        }
    }
}