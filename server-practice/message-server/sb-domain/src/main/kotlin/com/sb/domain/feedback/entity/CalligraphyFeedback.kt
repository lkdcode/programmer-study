package com.sb.domain.feedback.entity

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User
import com.sb.domain.feedback.value.FeedbackContent
import java.time.Instant

data class CalligraphyFeedback(
    val id: CalligraphyFeedbackId,
    val calligraphyId: Calligraphy.CalligraphyId,
    val user: User,
    val content: FeedbackContent,
    val createdAt: Instant,
) {
    @JvmInline
    value class CalligraphyFeedbackId(val value: Long)
}