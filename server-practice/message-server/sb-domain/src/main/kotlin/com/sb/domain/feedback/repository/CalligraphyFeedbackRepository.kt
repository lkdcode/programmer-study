package com.sb.domain.feedback.repository

import com.sb.domain.feedback.aggregate.CalligraphyFeedbackAggregate

interface CalligraphyFeedbackRepository {
    fun save(aggregate: CalligraphyFeedbackAggregate): CalligraphyFeedbackAggregate
}