package com.sb.application.like.ports.input.query

import com.sb.application.calligraphy.dto.query.*
import com.sb.domain.calligraphy.entity.Calligraphy

interface LikeCalligraphyQueryUsecase {
    suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesPageQuery): ShowcaseCalligraphyPage
    suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesSliceQuery): ShowcaseCalligraphySlice
    suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesCursorQuery): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>
}