package com.sb.domain.like.repository

import com.sb.domain.like.aggregate.CalligraphyLikeAggregate

interface CalligraphyLikeRepository {
    fun save(aggregate: CalligraphyLikeAggregate): CalligraphyLikeAggregate
}