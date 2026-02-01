package com.sb.adapter.calligraphy.input.event

import com.sb.application.calligraphy.event.CalligraphyGenerationEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CalligraphyGenerationEventHandler(
) {
    @EventListener
    fun handle(event: CalligraphyGenerationEvent) {
        // TODO()
    }
}