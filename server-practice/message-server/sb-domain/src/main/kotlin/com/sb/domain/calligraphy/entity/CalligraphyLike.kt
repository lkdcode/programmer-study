package com.sb.domain.calligraphy.entity

import com.sb.domain.calligraphy.value.User
import java.time.Instant

data class CalligraphyLike(
    val id: CalligraphyLikeId,
    val calligraphyId: Calligraphy.CalligraphyId,
    val user: User,
    val createdAt: Instant,
) {
    @JvmInline
    value class CalligraphyLikeId(val value: Long)
}