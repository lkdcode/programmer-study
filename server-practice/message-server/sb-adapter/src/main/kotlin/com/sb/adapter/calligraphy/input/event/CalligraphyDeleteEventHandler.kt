package com.sb.adapter.calligraphy.input.event

import com.sb.application.calligraphy.event.CalligraphyDeleteEvent
import com.sb.framework.log.logInfo
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component


@Component
class CalligraphyDeleteEventHandler(

) {

    @EventListener
    fun handle(event: CalligraphyDeleteEvent) {
        logInfo("TODO: CalligraphyDeleteEventHandler $event")
    }
}