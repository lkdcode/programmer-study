package com.sb.domain.calligraphy.aggregate

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.entity.CalligraphyLike
import com.sb.domain.calligraphy.value.User
import java.time.Instant

class CalligraphyLikeAggregate private constructor(
    private val like: CalligraphyLike
) {
    val getLike: CalligraphyLike get() = like

    companion object {
        fun create(
            calligraphyId: Calligraphy.CalligraphyId,
            user: User,
        ): CalligraphyLikeAggregate = CalligraphyLikeAggregate(
            CalligraphyLike(
                id = generateLikeId(),
                calligraphyId = calligraphyId,
                user = user,
                createdAt = Instant.now(),
            )
        )

        private fun generateLikeId(): CalligraphyLike.CalligraphyLikeId =
            CalligraphyLike.CalligraphyLikeId(Instant.now().toEpochMilli())
    }
}


