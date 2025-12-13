package com.sb.domain.like.repository

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User

interface CalligraphyLikeQueryRepository {
    fun existsBy(calligraphyId: Calligraphy.CalligraphyId, user: User): Boolean
    fun countBy(calligraphyId: Calligraphy.CalligraphyId): Long
}