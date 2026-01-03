package com.sb.domain.feedback.entity

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.feedback.value.FeedbackContent
import com.sb.domain.user.entity.User

data class CalligraphyFeedback(
    val id: CalligraphyFeedbackId,
    val calligraphyId: Calligraphy.CalligraphyId,
    val userId: User.UserId,
    val content: FeedbackContent,
) {
    @JvmInline
    value class CalligraphyFeedbackId(val value: Long)
}