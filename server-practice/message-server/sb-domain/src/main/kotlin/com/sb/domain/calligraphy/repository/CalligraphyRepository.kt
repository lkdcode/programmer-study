package com.sb.domain.calligraphy.repository

import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate

interface CalligraphyRepository {
    suspend fun save(aggregate: CalligraphyAggregate): CalligraphyAggregate
}