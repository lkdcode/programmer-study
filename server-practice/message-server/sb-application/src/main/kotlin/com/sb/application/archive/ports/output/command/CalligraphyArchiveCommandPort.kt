package com.sb.application.archive.ports.output.command

import com.sb.domain.archive.aggregate.CalligraphyArchiveAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author

interface CalligraphyArchiveCommandPort {
    suspend fun save(aggregate: CalligraphyArchiveAggregate): CalligraphyArchiveAggregate
    suspend fun deleteBy(calligraphyId: Calligraphy.CalligraphyId, user: Author)
}