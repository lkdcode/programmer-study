package com.sb.adapter.plan.output.validator

import com.sb.adapter.plan.output.infrastructure.r2dbc.repository.CreditWalletR2dbcRepository
import com.sb.application.common.validator.throwIf
import com.sb.application.credit.ports.output.validator.CreditWalletValidator
import com.sb.domain.credit.exception.CreditErrorCode
import com.sb.domain.user.entity.User
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component

@Component
class CreditWalletValidationAdapter(
    private val repository: CreditWalletR2dbcRepository,
) : CreditWalletValidator {

    override suspend fun requireWalletNotExists(userId: User.UserId) {
        val exists = repository
            .existsByUserId(userId.value)
            .awaitSingle()

        throwIf(exists, CreditErrorCode.WALLET_ALREADY_EXISTS)
    }
}