package com.sb.adapter.like.output.validator

import com.sb.adapter.like.output.infrastructure.r2dbc.mapper.vo.userIdVo
import com.sb.adapter.like.output.infrastructure.r2dbc.repository.CalligraphyLikeR2dbcRepository
import com.sb.adapter.like.output.infrastructure.r2dbc.repository.loadById
import com.sb.application.common.validator.throwIf
import com.sb.application.like.ports.validator.LikeCalligraphyValidator
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.like.entity.CalligraphyLike
import com.sb.domain.user.entity.User
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component


@Component
class LikeCalligraphyValidationAdapter(
    private val repository: CalligraphyLikeR2dbcRepository
) : LikeCalligraphyValidator {

    override suspend fun requireNotLiked(
        calligraphyId: Calligraphy.CalligraphyId,
        userId: User.UserId
    ) {
        val exists = repository
            .existsByCalligraphyIdAndUserId(calligraphyId.value, userId.value)
            .awaitFirstOrNull()

        throwIf(exists, IllegalArgumentException("TODO requireNotLiked"))
    }

    override suspend fun requireLiked(
        likeId: CalligraphyLike.CalligraphyLikeId,
        userId: User.UserId
    ) {
        val entity = repository
            .loadById(likeId)
            .awaitSingle()

        throwIf(entity.userIdVo != userId, IllegalArgumentException("TODO requireLiked"))
    }
}