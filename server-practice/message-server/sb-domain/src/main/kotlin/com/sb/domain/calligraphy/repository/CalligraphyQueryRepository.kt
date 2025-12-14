package com.sb.domain.calligraphy.repository

import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User

interface CalligraphyQueryRepository {
    suspend fun findById(id: Calligraphy.CalligraphyId): CalligraphyAggregate?
    suspend fun findByUser(user: User): List<CalligraphyAggregate>
}