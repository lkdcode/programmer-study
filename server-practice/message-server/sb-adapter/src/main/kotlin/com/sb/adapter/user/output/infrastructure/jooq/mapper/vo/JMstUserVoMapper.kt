package com.sb.adapter.user.output.infrastructure.jooq.mapper.vo

import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Nickname
import com.sb.jooq.tables.records.JMstUserRecord


val JMstUserRecord.toAuthorVo
    get(): Author = Author(User.UserId(this.id!!))

val JMstUserRecord.toNicknameVo
    get(): Nickname = Nickname.of(this.nickname)