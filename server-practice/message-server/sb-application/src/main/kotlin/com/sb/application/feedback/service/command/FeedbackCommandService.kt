package com.sb.application.feedback.service.command

import com.sb.application.feedback.ports.input.command.FeedbackCommandUsecase
import com.sb.application.feedback.ports.output.command.CalligraphyFeedbackCommandPort
import com.sb.application.feedback.ports.output.validator.FeedbackValidator
import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class FeedbackCommandService(
    private val feedbackValidator: FeedbackValidator,
    private val commandPort: CalligraphyFeedbackCommandPort,
) : FeedbackCommandUsecase {

    override suspend fun delete(
        userId: User.UserId,
        feedBackId: CalligraphyFeedback.CalligraphyFeedbackId
    ) {
        feedbackValidator.requireOwner(userId, feedBackId)
        commandPort.delete(feedBackId)
    }
}