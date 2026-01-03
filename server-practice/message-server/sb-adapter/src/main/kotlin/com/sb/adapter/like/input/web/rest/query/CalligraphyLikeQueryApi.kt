package com.sb.adapter.like.input.web.rest.query

import com.sb.adapter.like.input.web.rest.query.request.mapper.toGetLikedCalligraphiesCursorQuery
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.like.ports.input.query.LikeCalligraphyQueryUsecase
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.success
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CalligraphyLikeQueryApi(
    private val likeCalligraphyQueryUsecase: LikeCalligraphyQueryUsecase
) {

    @GetMapping("/api/calligraphies/like")
    suspend fun fetchCursor(
        @AuthenticationPrincipal auth: UserAuthentication,
        @RequestParam(required = false) calligraphyId: Long? = null,
        @PageableDefault(size = 10, page = 0) pageable: Pageable,
    ): ApiResponseEntity<ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>> {
        val query = toGetLikedCalligraphiesCursorQuery(auth, calligraphyId, pageable)
        val response = likeCalligraphyQueryUsecase.fetchMyLikedCalligraphies(query)

        return success(response)
    }
}