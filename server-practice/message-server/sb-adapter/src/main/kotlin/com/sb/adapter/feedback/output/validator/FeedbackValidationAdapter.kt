package com.sb.adapter.feedback.output.validator

import com.sb.adapter.feedback.output.infrastructure.r2dbc.repository.CalligraphyFeedbackR2dbcRepository
import com.sb.adapter.feedback.output.infrastructure.r2dbc.repository.loadById
import com.sb.application.common.validator.throwIf
import com.sb.application.feedback.ports.output.validator.FeedbackValidator
import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.domain.user.entity.User
import com.sb.framework.api.ApiException
import com.sb.framework.api.ApiResponseCode
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component


@Component
class FeedbackValidationAdapter(
    private val repository: CalligraphyFeedbackR2dbcRepository
) : FeedbackValidator {

    override suspend fun requireOwner(
        userId: User.UserId,
        id: CalligraphyFeedback.CalligraphyFeedbackId
    ) {
        val entity = repository
            .loadById(id)
            .awaitSingle()

        throwIf(entity.userId != userId.value, ApiException(ApiResponseCode.FEEDBACK_INVALID_OWNER))
    }
}