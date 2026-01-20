package com.sb.adapter.archive.output.infrastructure.jooq.mapper.vo

import com.sb.domain.archive.entity.CalligraphyArchive
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.entity.User
import com.sb.jooq.tables.records.JMstCalligraphyArchiveRecord
import java.time.Instant

val JMstCalligraphyArchiveRecord.toArchiveIdVo: CalligraphyArchive.CalligraphyArchiveId
    get() = CalligraphyArchive.CalligraphyArchiveId(this.id!!)

val JMstCalligraphyArchiveRecord.toCalligraphyIdVo: Calligraphy.CalligraphyId
    get() = Calligraphy.CalligraphyId(this.calligraphyId!!)

val JMstCalligraphyArchiveRecord.toUserIdVo: User.UserId
    get() = User.UserId(this.userId!!)

val JMstCalligraphyArchiveRecord.toAuthorVo: Author
    get() = Author(this.toUserIdVo)

val JMstCalligraphyArchiveRecord.createdAtToInstant: Instant
    get() = this.createdAt!!.toInstant()
