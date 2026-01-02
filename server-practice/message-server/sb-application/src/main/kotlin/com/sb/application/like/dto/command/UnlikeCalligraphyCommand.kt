package com.sb.application.like.dto.command

import com.sb.domain.like.entity.CalligraphyLike
import com.sb.domain.user.entity.User

data class UnlikeCalligraphyCommand(
    val likeId: CalligraphyLike.CalligraphyLikeId,
    val userId: User.UserId,
)