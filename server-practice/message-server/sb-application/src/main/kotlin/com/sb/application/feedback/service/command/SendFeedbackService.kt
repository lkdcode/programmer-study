package com.sb.application.feedback.service.command

import com.sb.application.calligraphy.ports.output.validator.CalligraphyValidator
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
    private val calligraphyValidator: CalligraphyValidator,
) : SendFeedbackUsecase {

    override suspend fun send(command: CreateFeedbackCommand): CalligraphyFeedbackAggregate {
        calligraphyValidator.validateOwnership(command.userId, command.calligraphyId)

        return commandPort.save(command)
    }
}