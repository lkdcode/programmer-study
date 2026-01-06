package com.sb.adapter.feedback.output.infrastructure.jooq.mapper.dto

import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.domain.feedback.value.FeedbackContent

data class JMstCalligraphyFeedbackDto(
    val feedbackId: Long,
    val content: String,
) {
    val feedbackIdVo: CalligraphyFeedback.CalligraphyFeedbackId
        get() = CalligraphyFeedback.CalligraphyFeedbackId(feedbackId)

    val feedbackContentVo: FeedbackContent
        get() = FeedbackContent.of(content)
}