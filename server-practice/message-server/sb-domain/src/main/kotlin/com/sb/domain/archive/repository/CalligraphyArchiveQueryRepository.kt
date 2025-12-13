package com.sb.domain.archive.repository

import com.sb.domain.archive.aggregate.CalligraphyArchiveAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User

interface CalligraphyArchiveQueryRepository {
    fun existsBy(calligraphyId: Calligraphy.CalligraphyId, user: User): Boolean
    fun findByUser(user: User): List<CalligraphyArchiveAggregate>
    fun countBy(calligraphyId: Calligraphy.CalligraphyId): Long
}