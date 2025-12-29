package com.sb.application.like.ports.validator

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.like.entity.CalligraphyLike
import com.sb.domain.user.entity.User

interface LikeCalligraphyValidator {
    suspend fun requireNotLiked(calligraphyId: Calligraphy.CalligraphyId, userId: User.UserId)
    suspend fun requireLiked(likeId: CalligraphyLike.CalligraphyLikeId, userId: User.UserId)
}