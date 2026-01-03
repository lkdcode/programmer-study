package com.sb.domain.feedback.aggregate

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.domain.feedback.entity.CalligraphyFeedback.CalligraphyFeedbackId
import com.sb.domain.feedback.value.FeedbackContent
import com.sb.domain.user.entity.User

class CalligraphyFeedbackAggregate private constructor(
    private val feedback: CalligraphyFeedback
) {
    val getFeedback: CalligraphyFeedback get() = feedback

    companion object {
        fun create(
            id: CalligraphyFeedbackId,
            calligraphyId: Calligraphy.CalligraphyId,
            userId: User.UserId,
            content: FeedbackContent,
        ): CalligraphyFeedbackAggregate = CalligraphyFeedbackAggregate(
            CalligraphyFeedback(
                id = id,
                calligraphyId = calligraphyId,
                userId = userId,
                content = content,
            )
        )
    }
}