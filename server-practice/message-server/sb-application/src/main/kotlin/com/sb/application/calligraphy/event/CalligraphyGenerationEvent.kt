package com.sb.application.calligraphy.event

import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate

class CalligraphyGenerationEvent(
    val aggregate: CalligraphyAggregate,
) {
}