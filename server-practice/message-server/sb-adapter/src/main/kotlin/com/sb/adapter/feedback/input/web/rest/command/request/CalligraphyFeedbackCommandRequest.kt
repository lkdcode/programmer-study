package com.sb.adapter.feedback.input.web.rest.command.request

import com.sb.application.feedback.dto.command.CreateFeedbackCommand
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.feedback.value.FeedbackContent
import com.sb.framework.security.authentication.UserAuthentication
import java.util.UUID

interface CalligraphyFeedbackCommandRequest {
    data class Create(
        val content: String
    ) {
        fun toCommand(
            auth: UserAuthentication,
            calligraphyId: UUID,
        ): CreateFeedbackCommand = CreateFeedbackCommand(
            calligraphyId = Calligraphy.CalligraphyId(calligraphyId),
            userId = auth.userIdVo,
            content = FeedbackContent.of(content),
        )
    }
}