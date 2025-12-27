package com.sb.application.calligraphy.service

import com.sb.application.calligraphy.event.CalligraphyDeleteEvent
import com.sb.application.calligraphy.ports.input.command.DeleteCalligraphyUsecase
import com.sb.application.calligraphy.ports.output.command.CalligraphyCommandPort
import com.sb.application.calligraphy.ports.output.publish.CalligraphyEventPublisher
import com.sb.application.calligraphy.ports.output.validator.CalligraphyValidator
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class DeleteCalligraphyService(
    private val calligraphyValidator: CalligraphyValidator,
    private val calligraphyCommandPort: CalligraphyCommandPort,
    private val calligraphyEventPublisher: CalligraphyEventPublisher,
) : DeleteCalligraphyUsecase {

    override suspend fun delete(
        userId: User.UserId,
        calligraphyId: Calligraphy.CalligraphyId
    ) {
        calligraphyValidator.validateOwnership(userId, calligraphyId)
        calligraphyCommandPort.delete(calligraphyId)
        calligraphyEventPublisher.publish(CalligraphyDeleteEvent(calligraphyId))
    }
}