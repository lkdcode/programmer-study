package com.sb.domain.like.aggregate

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.like.entity.CalligraphyLike
import com.sb.domain.like.entity.NewCalligraphyLike
import com.sb.domain.like.policy.LikePolicy
import com.sb.domain.user.entity.User

class CalligraphyLikeAggregate private constructor(
    private val like: CalligraphyLike
) {
    val snapshot get() = like

    companion object {
        fun create(
            calligraphyId: Calligraphy.CalligraphyId,
            userId: User.UserId,
            policy: LikePolicy
        ): NewCalligraphyLike {
            policy.requireNotLiked(calligraphyId, userId)

            return NewCalligraphyLike(
                calligraphyId = calligraphyId,
                userId = userId
            )
        }
    }
}