package com.sb.application.like.service.query

import com.sb.application.calligraphy.dto.query.*
import com.sb.application.like.ports.input.query.LikeCalligraphyQueryUsecase
import com.sb.application.like.ports.output.query.CalligraphyLikeQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetLikedCalligraphyService(
    private val queryPort: CalligraphyLikeQueryPort,
) : LikeCalligraphyQueryUsecase {

    override suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesPageQuery): ShowcaseCalligraphyPage =
        queryPort.findLikedByUser(query.user, query.pageRequest)

    override suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesSliceQuery): ShowcaseCalligraphySlice =
        queryPort.findLikedByUser(query.user, query.slicePageRequest)

    override suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesCursorQuery): ShowcaseCalligraphyCursor =
        queryPort.findLikedByUser(query.user, query.cursorPageRequest)
}