package com.sb.adapter.feedback.input.web.rest.command

import com.sb.adapter.feedback.input.web.rest.command.request.CalligraphyFeedbackCommandRequest
import com.sb.application.feedback.ports.input.command.FeedbackCommandUsecase
import com.sb.application.feedback.ports.input.command.SendFeedbackUsecase
import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.created
import com.sb.framework.api.deleted
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class CalligraphyFeedbackCommandApi(
    private val sendFeedbackUsecase: SendFeedbackUsecase,
    private val feedbackCommandUsecase: FeedbackCommandUsecase
) {

    @PostMapping("/api/calligraphies/{calligraphyId}/feedbacks")
    suspend fun create(
        @AuthenticationPrincipal auth: UserAuthentication,
        @PathVariable(name = "calligraphyId") calligraphyId: UUID,
        @RequestBody request: CalligraphyFeedbackCommandRequest.Create,
    ): ApiResponseEntity<Unit> {
        val command = request.toCommand(auth, calligraphyId)
        sendFeedbackUsecase.send(command)

        return created<Unit>()
    }

    @DeleteMapping("/api/calligraphies/feedbacks/{feedbackId}")
    suspend fun delete(
        @AuthenticationPrincipal auth: UserAuthentication,
        @PathVariable(name = "feedbackId") feedbackId: Long
    ): ApiResponseEntity<Unit> {
        feedbackCommandUsecase.delete(
            auth.userIdVo, CalligraphyFeedback.CalligraphyFeedbackId(feedbackId)
        )

        return deleted<Unit>()
    }
}