package com.sb.adapter.archive.output.infrastructure.r2dbc.mapper.vo

import com.sb.adapter.archive.output.infrastructure.r2dbc.entity.CalligraphyArchiveR2dbcEntity
import com.sb.domain.archive.entity.CalligraphyArchive
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.entity.User

val CalligraphyArchiveR2dbcEntity.archiveIdVo: CalligraphyArchive.CalligraphyArchiveId
    get() = CalligraphyArchive.CalligraphyArchiveId(id!!)

val CalligraphyArchiveR2dbcEntity.calligraphyIdVo: Calligraphy.CalligraphyId
    get() = Calligraphy.CalligraphyId(calligraphyId)

val CalligraphyArchiveR2dbcEntity.userIdVo: User.UserId
    get() = User.UserId(userId)

val CalligraphyArchiveR2dbcEntity.authorVo: Author
    get() = Author(userIdVo)
