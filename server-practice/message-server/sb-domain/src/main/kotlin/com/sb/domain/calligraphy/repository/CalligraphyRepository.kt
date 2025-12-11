package com.sb.domain.calligraphy.repository

import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate

interface CalligraphyRepository {
    fun save(aggregate: CalligraphyAggregate): CalligraphyAggregate
    fun delete(aggregate: CalligraphyAggregate): CalligraphyAggregate
}