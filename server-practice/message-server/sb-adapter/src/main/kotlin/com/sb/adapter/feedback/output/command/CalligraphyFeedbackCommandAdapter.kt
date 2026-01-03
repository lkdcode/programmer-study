package com.sb.adapter.feedback.output.command

import com.sb.adapter.feedback.output.infrastructure.r2dbc.mapper.entity.toAggregate
import com.sb.adapter.feedback.output.infrastructure.r2dbc.mapper.entity.toEntity
import com.sb.adapter.feedback.output.infrastructure.r2dbc.repository.CalligraphyFeedbackR2dbcRepository
import com.sb.application.feedback.dto.command.CreateFeedbackCommand
import com.sb.application.feedback.ports.output.command.CalligraphyFeedbackCommandPort
import com.sb.domain.feedback.aggregate.CalligraphyFeedbackAggregate
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component


@Component
class CalligraphyFeedbackCommandAdapter(
    private val repository: CalligraphyFeedbackR2dbcRepository
) : CalligraphyFeedbackCommandPort {

    override suspend fun save(command: CreateFeedbackCommand): CalligraphyFeedbackAggregate =
        repository
            .save(command.toEntity())
            .map { it.toAggregate() }
            .awaitSingle()
}