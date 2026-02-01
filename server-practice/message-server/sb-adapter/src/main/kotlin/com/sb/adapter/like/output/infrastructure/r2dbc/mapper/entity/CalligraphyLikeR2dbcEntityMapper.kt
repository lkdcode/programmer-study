package com.sb.adapter.like.output.infrastructure.r2dbc.mapper.entity

import com.sb.adapter.like.output.infrastructure.r2dbc.entity.CalligraphyLikeR2dbcEntity
import com.sb.adapter.like.output.infrastructure.r2dbc.mapper.vo.calligraphyIdVo
import com.sb.adapter.like.output.infrastructure.r2dbc.mapper.vo.likeIdVo
import com.sb.adapter.like.output.infrastructure.r2dbc.mapper.vo.userIdVo
import com.sb.domain.like.aggregate.CalligraphyLikeAggregate
import com.sb.domain.like.entity.NewCalligraphyLike


fun CalligraphyLikeR2dbcEntity.toAggregate(): CalligraphyLikeAggregate = CalligraphyLikeAggregate.create(
    id = likeIdVo,
    calligraphyId = calligraphyIdVo,
    userId = userIdVo,
)

fun NewCalligraphyLike.toEntity(): CalligraphyLikeR2dbcEntity = CalligraphyLikeR2dbcEntity(
    calligraphyId = calligraphyId.value,
    userId = userId.value,
)