package com.sb.domain.archive.repository

import com.sb.domain.archive.aggregate.CalligraphyArchiveAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User

interface CalligraphyArchiveRepository {
    fun save(aggregate: CalligraphyArchiveAggregate): CalligraphyArchiveAggregate
    fun deleteBy(calligraphyId: Calligraphy.CalligraphyId, user: User)
}