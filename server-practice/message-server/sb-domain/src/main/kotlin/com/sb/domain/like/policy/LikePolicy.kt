package com.sb.domain.like.policy

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.like.entity.CalligraphyLike
import com.sb.domain.user.entity.User

interface LikePolicy {
    fun requireNotLiked(calligraphyId: Calligraphy.CalligraphyId, userId: User.UserId)
    fun requireLiked(likeId: CalligraphyLike.CalligraphyLikeId, userId: User.UserId)
}