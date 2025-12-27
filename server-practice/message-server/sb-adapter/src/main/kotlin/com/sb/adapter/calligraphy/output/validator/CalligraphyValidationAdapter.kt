package com.sb.adapter.calligraphy.output.validator

import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.repository.CalligraphyR2dbcRepository
import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.repository.loadById
import com.sb.application.calligraphy.ports.output.validator.CalligraphyValidator
import com.sb.application.common.validator.monoErrorIf
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.exception.CalligraphyErrorCode
import com.sb.domain.user.entity.User
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Component


@Component
class CalligraphyValidationAdapter(
    private val repository: CalligraphyR2dbcRepository,
) : CalligraphyValidator {

    override suspend fun validateOwnership(
        userId: User.UserId,
        calligraphyId: Calligraphy.CalligraphyId
    ) {
        repository
            .loadById(calligraphyId)
            .flatMap {
                monoErrorIf(it.userId != userId.value, CalligraphyErrorCode.DELETE_DENIED)
            }
            .awaitFirstOrNull()
    }
}