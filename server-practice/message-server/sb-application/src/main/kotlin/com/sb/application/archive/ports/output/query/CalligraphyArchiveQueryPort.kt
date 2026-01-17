package com.sb.application.archive.ports.output.query

import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyPage
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphySlice
import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.domain.archive.aggregate.CalligraphyArchiveAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.entity.User

interface CalligraphyArchiveQueryPort {
    suspend fun existsBy(calligraphyId: Calligraphy.CalligraphyId, user: Author): Boolean
    suspend fun notExistsBy(calligraphyId: Calligraphy.CalligraphyId, user: Author): Boolean =
        !existsBy(calligraphyId, user)

    suspend fun findByUser(user: Author): List<CalligraphyArchiveAggregate>
    suspend fun countBy(calligraphyId: Calligraphy.CalligraphyId): Long

    suspend fun findArchivedByUser(userId: User.UserId, pageRequest: PageRequest): ShowcaseCalligraphyPage
    suspend fun findArchivedByUser(userId: User.UserId, pageRequest: SlicePageRequest): ShowcaseCalligraphySlice
    suspend fun findArchivedByUser(
        userId: User.UserId,
        pageRequest: CursorPageRequest<Calligraphy.CalligraphyId>
    ): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>
}