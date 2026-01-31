package com.sb.adapter.calligraphy.output.infrastructure.r2dbc.repository

import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.entity.CalligraphyR2dbcEntity
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.framework.api.ApiException
import com.sb.framework.api.ApiResponseCode
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono
import java.util.*

interface CalligraphyR2dbcRepository : R2dbcRepository<CalligraphyR2dbcEntity, UUID> {
}

fun CalligraphyR2dbcRepository.loadById(calligraphyId: Calligraphy.CalligraphyId) =
    findById(calligraphyId.value).switchIfEmpty(Mono.error(ApiException(ApiResponseCode.CALLIGRAPHY_NOT_FOUND)))