package com.sb.application.like.ports.input.query

import com.sb.application.calligraphy.dto.GetLikedCalligraphyQuery
import com.sb.domain.calligraphy.model.ShowcaseCalligraphy

interface LikeCalligraphyQueryUsecase {
    suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphyQuery): List<ShowcaseCalligraphy>
}