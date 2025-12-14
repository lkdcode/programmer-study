package com.sb.domain.like.repository

import com.sb.domain.like.aggregate.CalligraphyLikeAggregate

interface CalligraphyLikeRepository {
    suspend fun save(aggregate: CalligraphyLikeAggregate): CalligraphyLikeAggregate
}