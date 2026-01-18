package com.sb.adapter.calligraphy.input.web.rest.query

import com.sb.adapter.calligraphy.input.web.rest.query.request.mapper.toGetShowcaseFeedCursorQuery
import com.sb.adapter.calligraphy.input.web.rest.query.request.mapper.toGetUserCalligraphiesCursorQuery
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.ports.input.query.CalligraphyQueryUsecase
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.success
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class CalligraphyQueryApi(
    private val calligraphyQueryUsecase: CalligraphyQueryUsecase
) {

    @GetMapping("/api/calligraphies")
    suspend fun fetchShowcaseFeed(
        @AuthenticationPrincipal auth: UserAuthentication?,
        @RequestParam(required = false) calligraphyId: UUID? = null,
        @PageableDefault(size = 10) pageable: Pageable,
    ): ApiResponseEntity<ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>> {
        val query = toGetShowcaseFeedCursorQuery(auth, calligraphyId, pageable)
        val response = calligraphyQueryUsecase.fetchShowcaseFeed(query)

        return success(response)
    }

    @GetMapping("/api/calligraphies/users/{userId}")
    suspend fun fetchUserCalligraphies(
        @AuthenticationPrincipal auth: UserAuthentication?,
        @PathVariable userId: Long,
        @RequestParam(required = false) calligraphyId: UUID? = null,
        @PageableDefault(size = 10) pageable: Pageable,
    ): ApiResponseEntity<ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>> {
        val query = toGetUserCalligraphiesCursorQuery(auth, userId, calligraphyId, pageable)
        val response = calligraphyQueryUsecase.fetchUserCalligraphies(query)

        return success(response)
    }
}