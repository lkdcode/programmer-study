package com.sb.domain.calligraphy.repository

import com.sb.domain.calligraphy.aggregate.CalligraphyLikeAggregate

interface CalligraphyLikeRepository {
    fun save(aggregate: CalligraphyLikeAggregate): CalligraphyLikeAggregate
}