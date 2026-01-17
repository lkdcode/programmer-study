package com.sb.application.archive.service.query

import com.sb.application.archive.dto.query.GetArchivedCalligraphiesCursorQuery
import com.sb.application.archive.dto.query.GetArchivedCalligraphiesPageQuery
import com.sb.application.archive.dto.query.GetArchivedCalligraphiesSliceQuery
import com.sb.application.archive.ports.input.query.ArchiveCalligraphyQueryUsecase
import com.sb.application.archive.ports.output.query.CalligraphyArchiveQueryPort
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyPage
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphySlice
import com.sb.domain.calligraphy.entity.Calligraphy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetArchivedCalligraphyService(
    private val queryPort: CalligraphyArchiveQueryPort,
) : ArchiveCalligraphyQueryUsecase {

    override suspend fun fetchMyArchivedCalligraphies(query: GetArchivedCalligraphiesPageQuery): ShowcaseCalligraphyPage =
        queryPort.findArchivedByUser(query.user.userId, query.pageRequest)

    override suspend fun fetchMyArchivedCalligraphies(query: GetArchivedCalligraphiesSliceQuery): ShowcaseCalligraphySlice =
        queryPort.findArchivedByUser(query.user.userId, query.slicePageRequest)

    override suspend fun fetchMyArchivedCalligraphies(query: GetArchivedCalligraphiesCursorQuery): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId> =
        queryPort.findArchivedByUser(query.user.userId, query.cursorPageRequest)
}
