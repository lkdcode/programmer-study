package com.sb.application.calligraphy.service

import com.sb.application.calligraphy.event.CalligraphyGenerationEvent
import com.sb.application.calligraphy.ports.input.command.CreateCalligraphyCommandUsecase
import com.sb.application.calligraphy.ports.output.checker.CalligraphyCreditChecker
import com.sb.application.calligraphy.ports.output.command.CalligraphyCommandPort
import com.sb.application.calligraphy.ports.output.command.CalligraphyPaymentPort
import com.sb.application.calligraphy.ports.output.publish.CalligraphyEventPublisher
import com.sb.application.calligraphy.service.dsl.CreateCalligraphyDsl
import com.sb.domain.calligraphy.command.CreateCalligraphyCommand
import com.sb.domain.calligraphy.entity.Calligraphy.CalligraphyId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class CreateCalligraphyService(
    internal val commandPort: CalligraphyCommandPort,
    internal val calligraphyPaymentPort: CalligraphyPaymentPort,
    internal val calligraphyCreditChecker: CalligraphyCreditChecker,

    private val calligraphyEventPublisher: CalligraphyEventPublisher,
) : CreateCalligraphyCommandUsecase {

    override suspend fun create(command: CreateCalligraphyCommand): CalligraphyId =
        CreateCalligraphyDsl.execute(command) {
            validateCanCreate {
                checkCreditSufficiency()
            }
            pay()
            save(commandPort::save) { aggregate ->
                calligraphyEventPublisher.publish(CalligraphyGenerationEvent(aggregate))
            }
        }
}