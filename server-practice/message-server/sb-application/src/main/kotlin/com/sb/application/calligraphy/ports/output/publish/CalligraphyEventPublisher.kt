package com.sb.application.calligraphy.ports.output.publish

import com.sb.application.calligraphy.event.CalligraphyDeleteEvent
import com.sb.application.calligraphy.event.CalligraphyGenerationEvent

interface CalligraphyEventPublisher {
    suspend fun publish(event: CalligraphyGenerationEvent)
    suspend fun publish(event: CalligraphyDeleteEvent)
}