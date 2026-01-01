package com.sb.application.like.ports.input.query

import com.sb.application.calligraphy.dto.query.GetLikedCalligraphiesCursorQuery
import com.sb.application.calligraphy.dto.query.GetLikedCalligraphiesPageQuery
import com.sb.application.calligraphy.dto.query.GetLikedCalligraphiesSliceQuery
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyPage
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphySlice

interface LikeCalligraphyQueryUsecase {
    suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesPageQuery): ShowcaseCalligraphyPage
    suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesSliceQuery): ShowcaseCalligraphySlice
    suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphiesCursorQuery): ShowcaseCalligraphyCursor
}