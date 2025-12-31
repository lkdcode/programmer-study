package com.sb.adapter.user.output.infrastructure.jooq.mapper

import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.entity.User
import com.sb.jooq.tables.records.JMstUserRecord


fun JMstUserRecord.toAuthorVo(): Author = Author(User.UserId(this.id!!))