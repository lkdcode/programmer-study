package com.sb.adapter.calligraphy.output.validator

import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.repository.CalligraphyR2dbcRepository
import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.repository.loadById
import com.sb.application.calligraphy.ports.output.validator.CalligraphyValidator
import com.sb.application.common.validator.throwIf
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.user.entity.User
import com.sb.framework.api.ApiException
import com.sb.framework.api.ApiResponseCode
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component


@Component
class CalligraphyValidationAdapter(
    private val repository: CalligraphyR2dbcRepository,
) : CalligraphyValidator {

    override suspend fun validateOwnership(
        userId: User.UserId,
        calligraphyId: Calligraphy.CalligraphyId
    ) {
        val entity = repository
            .loadById(calligraphyId)
            .awaitSingle()

        throwIf(entity.userId != userId.value, ApiException(ApiResponseCode.INVALID)) // TODO : ApiCode
    }
}