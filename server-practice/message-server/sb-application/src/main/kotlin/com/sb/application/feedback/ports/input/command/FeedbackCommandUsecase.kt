package com.sb.application.feedback.ports.input.command

import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.domain.user.entity.User

interface FeedbackCommandUsecase {
    suspend fun delete(userId: User.UserId, feedBackId: CalligraphyFeedback.CalligraphyFeedbackId)
}