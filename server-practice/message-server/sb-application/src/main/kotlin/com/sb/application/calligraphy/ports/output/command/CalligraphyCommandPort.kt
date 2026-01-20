package com.sb.application.calligraphy.ports.output.command

import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate
import com.sb.application.calligraphy.dto.command.CreateCalligraphyCommand
import com.sb.domain.calligraphy.entity.Calligraphy

interface CalligraphyCommandPort {
    suspend fun save(aggregate: CalligraphyAggregate): CalligraphyAggregate
    suspend fun save(command: CreateCalligraphyCommand): CalligraphyAggregate

    suspend fun delete(calligraphyId: Calligraphy.CalligraphyId)
}