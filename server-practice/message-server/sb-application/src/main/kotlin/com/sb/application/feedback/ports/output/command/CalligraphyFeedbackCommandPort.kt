package com.sb.application.feedback.ports.output.command

import com.sb.application.feedback.dto.command.CreateFeedbackCommand
import com.sb.domain.feedback.aggregate.CalligraphyFeedbackAggregate

interface CalligraphyFeedbackCommandPort {
    suspend fun save(command: CreateFeedbackCommand): CalligraphyFeedbackAggregate
}