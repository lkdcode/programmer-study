package com.sb.application.feedback.ports.output.validator

import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.domain.user.entity.User

interface FeedbackValidator {
    suspend fun requireOwner(userId: User.UserId, id: CalligraphyFeedback.CalligraphyFeedbackId)
}