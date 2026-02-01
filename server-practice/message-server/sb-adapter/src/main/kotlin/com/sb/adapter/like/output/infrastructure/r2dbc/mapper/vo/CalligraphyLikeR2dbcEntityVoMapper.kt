package com.sb.adapter.like.output.infrastructure.r2dbc.mapper.vo

import com.sb.adapter.like.output.infrastructure.r2dbc.entity.CalligraphyLikeR2dbcEntity
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.like.entity.CalligraphyLike
import com.sb.domain.user.entity.User


val CalligraphyLikeR2dbcEntity.userIdVo
    get() = User.UserId(userId)

val CalligraphyLikeR2dbcEntity.calligraphyIdVo
    get() = Calligraphy.CalligraphyId(calligraphyId)

val CalligraphyLikeR2dbcEntity.likeIdVo
    get() = CalligraphyLike.CalligraphyLikeId(id!!)