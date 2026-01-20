package com.sb.adapter.archive.output.infrastructure.r2dbc.mapper.entity

import com.sb.adapter.archive.output.infrastructure.r2dbc.entity.CalligraphyArchiveR2dbcEntity
import com.sb.adapter.archive.output.infrastructure.r2dbc.mapper.vo.archiveIdVo
import com.sb.adapter.archive.output.infrastructure.r2dbc.mapper.vo.authorVo
import com.sb.adapter.archive.output.infrastructure.r2dbc.mapper.vo.calligraphyIdVo
import com.sb.domain.archive.aggregate.CalligraphyArchiveAggregate
import com.sb.domain.archive.entity.CalligraphyArchive

fun CalligraphyArchiveR2dbcEntity.toAggregate(): CalligraphyArchiveAggregate =
    CalligraphyArchiveAggregate.restore(
        CalligraphyArchive(
            id = archiveIdVo,
            calligraphyId = calligraphyIdVo,
            user = authorVo,
            createdAt = createdAt,
        )
    )

fun CalligraphyArchiveAggregate.toEntity(): CalligraphyArchiveR2dbcEntity =
    CalligraphyArchiveR2dbcEntity(
        calligraphyId = getArchive.calligraphyId.value,
        userId = getArchive.user.userId.value,
    )
