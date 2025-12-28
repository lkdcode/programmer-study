package com.sb.application.like.ports.output.command

import com.sb.application.like.dto.UnlikeCalligraphyCommand
import com.sb.domain.like.aggregate.CalligraphyLikeAggregate
import com.sb.domain.like.entity.NewCalligraphyLike

interface CalligraphyLikeCommandPort {
    suspend fun save(vo: NewCalligraphyLike): CalligraphyLikeAggregate
    suspend fun delete(command: UnlikeCalligraphyCommand)
}