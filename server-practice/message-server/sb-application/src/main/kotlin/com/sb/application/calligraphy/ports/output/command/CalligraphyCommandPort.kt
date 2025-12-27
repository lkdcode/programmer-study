package com.sb.application.calligraphy.ports.output.command

import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate
import com.sb.domain.calligraphy.command.CreateCalligraphyCommand

interface CalligraphyCommandPort {
    suspend fun save(aggregate: CalligraphyAggregate): CalligraphyAggregate
    suspend fun save(command: CreateCalligraphyCommand): CalligraphyAggregate
}