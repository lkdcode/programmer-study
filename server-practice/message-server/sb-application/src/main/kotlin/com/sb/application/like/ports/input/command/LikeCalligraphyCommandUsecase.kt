package com.sb.application.like.ports.input.command

import com.sb.domain.like.entity.CalligraphyLike
import com.sb.application.like.dto.LikeCalligraphyCommand
import com.sb.application.like.dto.UnlikeCalligraphyCommand

interface LikeCalligraphyCommandUsecase {
    suspend fun like(command: LikeCalligraphyCommand): CalligraphyLike.CalligraphyLikeId
    suspend fun unlike(command: UnlikeCalligraphyCommand)
}