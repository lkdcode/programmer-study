package com.sb.domain.like.service

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.User
import com.sb.domain.like.aggregate.CalligraphyLikeAggregate
import com.sb.domain.like.repository.CalligraphyLikeQueryRepository
import com.sb.domain.like.repository.CalligraphyLikeRepository


class CalligraphyLikeService(
    private val repository: CalligraphyLikeRepository,
    private val queryRepository: CalligraphyLikeQueryRepository,
) {
    fun like(calligraphyId: Calligraphy.CalligraphyId, user: User): CalligraphyLikeAggregate {
        require(!queryRepository.existsBy(calligraphyId, user)) { "이미 좋아요한 캘리그래피입니다." }

        val aggregate = CalligraphyLikeAggregate.create(calligraphyId, user)
        return repository.save(aggregate)
    }
}