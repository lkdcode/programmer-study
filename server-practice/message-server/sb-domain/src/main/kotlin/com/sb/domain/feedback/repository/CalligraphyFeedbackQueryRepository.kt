package com.sb.domain.feedback.repository

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.feedback.aggregate.CalligraphyFeedbackAggregate

interface CalligraphyFeedbackQueryRepository {
    suspend fun findByCalligraphyId(calligraphyId: Calligraphy.CalligraphyId): List<CalligraphyFeedbackAggregate>
}