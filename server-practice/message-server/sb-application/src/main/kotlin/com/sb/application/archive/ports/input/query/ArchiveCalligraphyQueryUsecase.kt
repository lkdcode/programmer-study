package com.sb.application.archive.ports.input.query

import com.sb.application.archive.dto.query.GetArchivedCalligraphiesCursorQuery
import com.sb.application.archive.dto.query.GetArchivedCalligraphiesPageQuery
import com.sb.application.archive.dto.query.GetArchivedCalligraphiesSliceQuery
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyPage
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphySlice
import com.sb.domain.calligraphy.entity.Calligraphy

interface ArchiveCalligraphyQueryUsecase {
    suspend fun fetchMyArchivedCalligraphies(query: GetArchivedCalligraphiesPageQuery): ShowcaseCalligraphyPage
    suspend fun fetchMyArchivedCalligraphies(query: GetArchivedCalligraphiesSliceQuery): ShowcaseCalligraphySlice
    suspend fun fetchMyArchivedCalligraphies(query: GetArchivedCalligraphiesCursorQuery): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>
}
