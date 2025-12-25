package com.sb.domain.calligraphy.repository

import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author

interface CalligraphyQueryRepository {
    suspend fun findById(id: Calligraphy.CalligraphyId): CalligraphyAggregate?
    suspend fun findByUser(user: Author): List<CalligraphyAggregate>
}