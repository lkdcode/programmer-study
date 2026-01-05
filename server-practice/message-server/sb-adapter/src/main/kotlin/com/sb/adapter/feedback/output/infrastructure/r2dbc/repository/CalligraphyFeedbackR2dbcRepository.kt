package com.sb.adapter.feedback.output.infrastructure.r2dbc.repository

import com.sb.adapter.feedback.output.infrastructure.r2dbc.entity.CalligraphyFeedbackR2dbcEntity
import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.framework.api.ApiException
import com.sb.framework.api.ApiResponseCode
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface CalligraphyFeedbackR2dbcRepository : R2dbcRepository<CalligraphyFeedbackR2dbcEntity, Long> {
    fun findByIdAndIsDeletedFalse(id: Long): Mono<CalligraphyFeedbackR2dbcEntity>
}

fun CalligraphyFeedbackR2dbcRepository.loadById(id: CalligraphyFeedback.CalligraphyFeedbackId): Mono<CalligraphyFeedbackR2dbcEntity> =
    findByIdAndIsDeletedFalse(id.value).switchIfEmpty(Mono.error(ApiException(ApiResponseCode.FEEDBACK_NOT_FOUND)))