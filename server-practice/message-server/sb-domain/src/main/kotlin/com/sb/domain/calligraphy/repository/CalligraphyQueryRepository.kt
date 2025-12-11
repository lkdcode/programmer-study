package com.sb.domain.calligraphy.repository

import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User

interface CalligraphyQueryRepository {
    fun findAll(): List<CalligraphyAggregate>

    fun findById(id: Calligraphy.CalligraphyId): CalligraphyAggregate?
    fun findByUser(user: User): List<CalligraphyAggregate>
}