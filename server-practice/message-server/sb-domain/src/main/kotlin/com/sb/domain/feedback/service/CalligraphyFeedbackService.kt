package com.sb.domain.feedback.service

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User
import com.sb.domain.feedback.aggregate.CalligraphyFeedbackAggregate
import com.sb.domain.feedback.repository.CalligraphyFeedbackRepository
import com.sb.domain.feedback.value.FeedbackContent

class CalligraphyFeedbackService(
    private val repository: CalligraphyFeedbackRepository,
) {
    fun sendFeedback(
        calligraphyId: Calligraphy.CalligraphyId,
        user: User,
        content: FeedbackContent,
    ): CalligraphyFeedbackAggregate {
        val aggregate = CalligraphyFeedbackAggregate.create(calligraphyId, user, content)
        return repository.save(aggregate)
    }
}