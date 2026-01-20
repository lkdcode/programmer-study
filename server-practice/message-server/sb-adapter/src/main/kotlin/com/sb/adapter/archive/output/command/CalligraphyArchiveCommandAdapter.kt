package com.sb.adapter.archive.output.command

import com.sb.adapter.archive.output.infrastructure.r2dbc.mapper.entity.toAggregate
import com.sb.adapter.archive.output.infrastructure.r2dbc.mapper.entity.toEntity
import com.sb.adapter.archive.output.infrastructure.r2dbc.repository.CalligraphyArchiveR2dbcRepository
import com.sb.application.archive.ports.output.command.CalligraphyArchiveCommandPort
import com.sb.domain.archive.aggregate.CalligraphyArchiveAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component

@Component
class CalligraphyArchiveCommandAdapter(
    private val repository: CalligraphyArchiveR2dbcRepository,
) : CalligraphyArchiveCommandPort {

    override suspend fun save(aggregate: CalligraphyArchiveAggregate): CalligraphyArchiveAggregate =
        repository
            .save(aggregate.toEntity())
            .map { it.toAggregate() }
            .awaitSingle()

    override suspend fun deleteBy(calligraphyId: Calligraphy.CalligraphyId, user: Author) {
        repository.deleteByCalligraphyIdAndUserId(calligraphyId.value, user.userId.value).awaitSingleOrNull()
    }
}