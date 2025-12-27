package com.sb.adapter.calligraphy.output.command

import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.mapper.toAggregate
import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.mapper.toR2dbcEntity
import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.repository.CalligraphyR2dbcRepository
import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.repository.loadById
import com.sb.application.calligraphy.ports.output.command.CalligraphyCommandPort
import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate
import com.sb.domain.calligraphy.command.CreateCalligraphyCommand
import com.sb.domain.calligraphy.entity.Calligraphy
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component


@Component
class CalligraphyCommandAdapter(
    private val repository: CalligraphyR2dbcRepository
) : CalligraphyCommandPort {

    override suspend fun save(aggregate: CalligraphyAggregate): CalligraphyAggregate =
        repository
            .save(aggregate.toR2dbcEntity())
            .awaitSingle()
            .toAggregate()

    override suspend fun save(command: CreateCalligraphyCommand): CalligraphyAggregate =
        repository
            .save(command.toR2dbcEntity())
            .awaitSingle()
            .toAggregate()

    override suspend fun delete(calligraphyId: Calligraphy.CalligraphyId) {
        repository
            .loadById(calligraphyId)
            .flatMap {
                it.delete()
                repository.save(it)
            }
            .awaitFirstOrNull()
    }
}