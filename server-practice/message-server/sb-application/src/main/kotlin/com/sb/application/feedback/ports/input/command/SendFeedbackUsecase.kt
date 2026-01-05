package com.sb.application.feedback.ports.input.command

import com.sb.application.feedback.dto.command.CreateFeedbackCommand
import com.sb.domain.feedback.aggregate.CalligraphyFeedbackAggregate

interface SendFeedbackUsecase {
    suspend fun send(command: CreateFeedbackCommand): CalligraphyFeedbackAggregate
}