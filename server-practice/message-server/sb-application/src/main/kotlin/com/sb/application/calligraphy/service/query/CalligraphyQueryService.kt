package com.sb.application.calligraphy.service.query

import com.sb.application.calligraphy.dto.query.*
import com.sb.application.calligraphy.ports.input.query.CalligraphyQueryUsecase
import com.sb.application.calligraphy.ports.output.query.CalligraphyShowcaseQueryPort
import com.sb.domain.calligraphy.entity.Calligraphy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CalligraphyQueryService(
    private val showcaseQueryPort: CalligraphyShowcaseQueryPort,
) : CalligraphyQueryUsecase {

    override suspend fun fetchShowcaseFeed(
        query: GetShowcaseFeedPageQuery,
    ): ShowcaseCalligraphyPage =
        showcaseQueryPort.findShowcaseFeed(query.viewer, query.pageRequest)

    override suspend fun fetchShowcaseFeed(
        query: GetShowcaseFeedSliceQuery,
    ): ShowcaseCalligraphySlice =
        showcaseQueryPort.findShowcaseFeed(query.viewer, query.slicePageRequest)

    override suspend fun fetchShowcaseFeed(
        query: GetShowcaseFeedCursorQuery,
    ): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId> =
        showcaseQueryPort.findShowcaseFeed(query.viewer, query.cursorPageRequest)

    override suspend fun fetchUserCalligraphies(
        query: GetUserCalligraphiesPageQuery,
    ): ShowcaseCalligraphyPage =
        showcaseQueryPort.findByAuthor(query.author, query.viewer, query.pageRequest)

    override suspend fun fetchUserCalligraphies(
        query: GetUserCalligraphiesSliceQuery,
    ): ShowcaseCalligraphySlice =
        showcaseQueryPort.findByAuthor(query.author, query.viewer, query.slicePageRequest)

    override suspend fun fetchUserCalligraphies(
        query: GetUserCalligraphiesCursorQuery,
    ): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId> =
        showcaseQueryPort.findByAuthor(query.author, query.viewer, query.cursorPageRequest)
}