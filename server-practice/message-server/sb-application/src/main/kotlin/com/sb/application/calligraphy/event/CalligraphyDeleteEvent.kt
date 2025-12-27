package com.sb.application.calligraphy.event

import com.sb.domain.calligraphy.entity.Calligraphy

class CalligraphyDeleteEvent(
    val id: Calligraphy.CalligraphyId,
) {
}