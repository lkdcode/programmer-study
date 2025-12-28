package com.sb.application.like.dto

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.like.aggregate.CalligraphyLikeAggregate
import com.sb.domain.like.entity.NewCalligraphyLike
import com.sb.domain.like.policy.LikePolicy
import com.sb.domain.user.entity.User

data class LikeCalligraphyCommand(
    val calligraphyId: Calligraphy.CalligraphyId,
    val userId: User.UserId,
) {
    fun convert(policy: LikePolicy): NewCalligraphyLike = CalligraphyLikeAggregate.create(
        calligraphyId = calligraphyId,
        userId = userId,
        policy = policy,
    )
}