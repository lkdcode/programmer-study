package com.sb.domain.like.repository

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User

interface CalligraphyLikeQueryRepository {
    suspend fun existsBy(calligraphyId: Calligraphy.CalligraphyId, user: User): Boolean
    suspend fun countBy(calligraphyId: Calligraphy.CalligraphyId): Long
}