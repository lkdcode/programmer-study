package com.sb.domain.like.entity

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.user.entity.User

data class NewCalligraphyLike(
    val calligraphyId: Calligraphy.CalligraphyId,
    val userId: User.UserId,
)