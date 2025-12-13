package com.sb.domain.feedback.aggregate

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User
import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.domain.feedback.value.FeedbackContent
import java.time.Instant

class CalligraphyFeedbackAggregate private constructor(
    private val feedback: CalligraphyFeedback
) {
    val getFeedback: CalligraphyFeedback get() = feedback

    companion object {
        fun create(
            calligraphyId: Calligraphy.CalligraphyId,
            user: User,
            content: FeedbackContent,
        ): CalligraphyFeedbackAggregate = CalligraphyFeedbackAggregate(
            CalligraphyFeedback(
                id = generateFeedbackId(),
                calligraphyId = calligraphyId,
                user = user,
                content = content,
                createdAt = Instant.now(),
            )
        )

        private fun generateFeedbackId(): CalligraphyFeedback.CalligraphyFeedbackId =
            CalligraphyFeedback.CalligraphyFeedbackId(Instant.now().toEpochMilli())
    }
}