package com.sb.application.calligraphy.ports.input.query

import com.sb.application.calligraphy.dto.query.*
import com.sb.domain.calligraphy.entity.Calligraphy

interface CalligraphyQueryUsecase {

    suspend fun fetchShowcaseFeed(query: GetShowcaseFeedPageQuery): ShowcaseCalligraphyPage
    suspend fun fetchShowcaseFeed(query: GetShowcaseFeedSliceQuery): ShowcaseCalligraphySlice
    suspend fun fetchShowcaseFeed(query: GetShowcaseFeedCursorQuery): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>

    suspend fun fetchUserCalligraphies(query: GetUserCalligraphiesPageQuery): ShowcaseCalligraphyPage
    suspend fun fetchUserCalligraphies(query: GetUserCalligraphiesSliceQuery): ShowcaseCalligraphySlice
    suspend fun fetchUserCalligraphies(query: GetUserCalligraphiesCursorQuery): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>
}