package com.sb.adapter.calligraphy.output.infrastructure.r2dbc.mapper

import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.entity.CalligraphyR2dbcEntity
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Prompt
import com.sb.domain.calligraphy.value.Seed
import com.sb.domain.calligraphy.value.Text
import com.sb.domain.user.entity.User


val CalligraphyR2dbcEntity.userIdVo get() = User.UserId(userId)

val CalligraphyR2dbcEntity.idVo get() = Calligraphy.CalligraphyId(id!!)

val CalligraphyR2dbcEntity.seedVo get() = Seed.of(seed)

val CalligraphyR2dbcEntity.textVo get() = Text.of(text)

val CalligraphyR2dbcEntity.promptVo: Prompt? get() = prompt?.let { Prompt.of(prompt) }