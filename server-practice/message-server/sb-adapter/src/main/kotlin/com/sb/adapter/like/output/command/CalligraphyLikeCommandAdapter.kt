package com.sb.adapter.like.output.command

import com.sb.adapter.like.output.infrastructure.r2dbc.mapper.entity.toAggregate
import com.sb.adapter.like.output.infrastructure.r2dbc.mapper.entity.toEntity
import com.sb.adapter.like.output.infrastructure.r2dbc.repository.CalligraphyLikeR2dbcRepository
import com.sb.adapter.like.output.infrastructure.r2dbc.repository.removeById
import com.sb.application.like.dto.command.UnlikeCalligraphyCommand
import com.sb.application.like.ports.output.command.CalligraphyLikeCommandPort
import com.sb.domain.like.aggregate.CalligraphyLikeAggregate
import com.sb.domain.like.entity.NewCalligraphyLike
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component


@Component
class CalligraphyLikeCommandAdapter(
    private val repository: CalligraphyLikeR2dbcRepository,
) : CalligraphyLikeCommandPort {

    override suspend fun save(vo: NewCalligraphyLike): CalligraphyLikeAggregate {
        return repository
            .save(vo.toEntity())
            .map { it.toAggregate() }
            .awaitSingle()
    }

    override suspend fun delete(command: UnlikeCalligraphyCommand) {
        repository.removeById(command.likeId)
    }
}