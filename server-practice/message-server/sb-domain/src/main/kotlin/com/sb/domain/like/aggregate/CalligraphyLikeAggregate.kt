package com.sb.domain.like.aggregate

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.like.entity.CalligraphyLike
import com.sb.domain.like.entity.CalligraphyLike.CalligraphyLikeId
import com.sb.domain.like.entity.NewCalligraphyLike
import com.sb.domain.user.entity.User

class CalligraphyLikeAggregate private constructor(
    private val like: CalligraphyLike
) {
    val snapshot get() = like

    companion object {
        fun create(
            id: CalligraphyLikeId,
            calligraphyId: Calligraphy.CalligraphyId,
            userId: User.UserId,
        ): CalligraphyLikeAggregate = CalligraphyLikeAggregate(
            CalligraphyLike(
                id,
                calligraphyId,
                userId,
            )
        )

        fun createNewCalligraphyLike(
            calligraphyId: Calligraphy.CalligraphyId,
            userId: User.UserId,
        ): NewCalligraphyLike =
            NewCalligraphyLike(
                calligraphyId = calligraphyId,
                userId = userId
            )
    }
}