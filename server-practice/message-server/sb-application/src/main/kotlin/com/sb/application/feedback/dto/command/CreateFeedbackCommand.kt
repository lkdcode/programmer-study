package com.sb.application.feedback.dto.command

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.feedback.value.FeedbackContent
import com.sb.domain.user.entity.User

data class CreateFeedbackCommand(
    val calligraphyId: Calligraphy.CalligraphyId,
    val userId: User.UserId,
    val content: FeedbackContent,
)