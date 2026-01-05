package com.sb.application.feedback.service.command

import com.sb.application.feedback.dto.command.CreateFeedbackCommand
import com.sb.application.feedback.ports.input.command.SendFeedbackUsecase
import com.sb.application.feedback.ports.output.command.CalligraphyFeedbackCommandPort
import com.sb.domain.feedback.aggregate.CalligraphyFeedbackAggregate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SendFeedbackService(
    private val commandPort: CalligraphyFeedbackCommandPort,
) : SendFeedbackUsecase {

    override suspend fun send(command: CreateFeedbackCommand): CalligraphyFeedbackAggregate =
        commandPort.save(command)
}