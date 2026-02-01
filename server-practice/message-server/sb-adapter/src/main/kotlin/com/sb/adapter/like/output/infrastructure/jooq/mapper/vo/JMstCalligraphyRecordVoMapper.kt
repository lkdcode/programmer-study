package com.sb.adapter.like.output.infrastructure.jooq.mapper.vo

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.*
import com.sb.domain.user.entity.User
import com.sb.jooq.tables.records.JMstCalligraphyRecord
import java.time.Instant
import java.util.UUID


val JMstCalligraphyRecord.toCalligraphyIdVo: Calligraphy.CalligraphyId
    get(): Calligraphy.CalligraphyId = Calligraphy.CalligraphyId(this.get("id", UUID::class.java)!!)

val JMstCalligraphyRecord.toSeedVo: Seed
    get() = Seed.of(this.seed)

val JMstCalligraphyRecord.toTextVo: Text
    get() = Text.of(this.text)

val JMstCalligraphyRecord.toPromptVo: Prompt?
    get() = this.prompt?.let { Prompt.of(this.prompt!!) }

val JMstCalligraphyRecord.toStyleVo: StyleType
    get() = StyleType.valueOf(this.style)

val JMstCalligraphyRecord.toUserIdVo: User.UserId
    get() = User.UserId(this.userId)

val JMstCalligraphyRecord.toAuthorVo: Author
    get() = Author(this.toUserIdVo)

val JMstCalligraphyRecord.createdAtToInstant: Instant
    get() = this.createdAt!!.toInstant()
