package com.sb.adapter.user.output.infrastructure.r2dbc.mapper

import com.sb.adapter.user.output.infrastructure.r2dbc.entity.UserR2dbcEntity
import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email
import com.sb.domain.user.value.Nickname
import com.sb.domain.user.value.Password


val UserR2dbcEntity.userIdVo
    get() = User.UserId(requireNotNull(id))

val UserR2dbcEntity.emailVo
    get() = Email.of(email)

val UserR2dbcEntity.nicknameVo
    get() = Nickname.of(nickname)

val UserR2dbcEntity.passwordVo
    get() = password?.let { Password.encrypted(it) }

val UserR2dbcEntity.signUpTypeVo
    get() = signUpType