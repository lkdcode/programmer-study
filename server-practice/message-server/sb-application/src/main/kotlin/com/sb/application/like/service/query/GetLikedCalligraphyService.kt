package com.sb.application.like.service.query

import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyPage
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphySlice
import com.sb.application.like.dto.query.GetLikedCalligraphiesCursorQuery
import com.sb.application.like.dto.query.GetLikedCalligraphiesPageQuery
import com.sb.application.like.dto.query.GetLikedCalligraphiesSliceQuery
import com.sb.application.like.ports.input.query.LikeCalligraphyQueryUsecase
import com.sb.application.like.ports.output.query.CalligraphyLikeQueryPort
import com.sb.domain.calligraphy.entity.Calligraphy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetLikedCalligraphyService(
    private val queryPort: CalligraphyLikeQueryPort,
) : LikeCalligraphyQueryUsecase {

    override suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesPageQuery): ShowcaseCalligraphyPage =
        queryPort.findLikedByUser(query.user.userId, query.pageRequest)

    override suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesSliceQuery): ShowcaseCalligraphySlice =
        queryPort.findLikedByUser(query.user.userId, query.slicePageRequest)

    override suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesCursorQuery): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId> =
        queryPort.findLikedByUser(query.user.userId, query.cursorPageRequest)
}