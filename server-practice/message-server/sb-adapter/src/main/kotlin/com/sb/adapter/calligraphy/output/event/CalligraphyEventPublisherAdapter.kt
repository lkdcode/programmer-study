package com.sb.adapter.calligraphy.output.event

import com.sb.application.calligraphy.event.CalligraphyGenerationEvent
import com.sb.application.calligraphy.ports.output.publish.CalligraphyEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component


@Component
class CalligraphyEventPublisherAdapter(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : CalligraphyEventPublisher {

    override suspend fun publish(event: CalligraphyGenerationEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}