package com.sb.domain.calligraphy.service

import com.sb.domain.calligraphy.aggregate.CalligraphyLikeAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.repository.CalligraphyLikeQueryRepository
import com.sb.domain.calligraphy.repository.CalligraphyLikeRepository
import com.sb.domain.calligraphy.value.User

/**
 * (정책) 동일 사용자는 동일 Calligraphy에 좋아요를 1번만 가능.
 *
 * Calligraphy 자체는 불변이므로, 좋아요는 별도 Aggregate로 관리한다.
 */
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


