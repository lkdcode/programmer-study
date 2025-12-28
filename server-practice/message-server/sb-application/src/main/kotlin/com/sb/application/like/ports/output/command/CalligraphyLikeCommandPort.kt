package com.sb.application.like.ports.output.command

import com.sb.application.like.dto.UnlikeCalligraphyCommand
import com.sb.domain.like.entity.CalligraphyLike
import com.sb.domain.like.entity.NewCalligraphyLike

interface CalligraphyLikeCommandPort {
    suspend fun save(entity: NewCalligraphyLike): CalligraphyLike
    suspend fun delete(command: UnlikeCalligraphyCommand)
}