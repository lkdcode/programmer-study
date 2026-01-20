package com.sb.adapter.archive.input.web.rest.query

import com.sb.adapter.archive.input.web.rest.query.request.mapper.toGetArchivedCalligraphiesCursorQuery
import com.sb.application.archive.ports.input.query.ArchiveCalligraphyQueryUsecase
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
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
import java.util.*

@RestController
class CalligraphyArchiveQueryApi(
    private val archiveCalligraphyQueryUsecase: ArchiveCalligraphyQueryUsecase,
) {

    @GetMapping("/api/calligraphies/archive")
    suspend fun fetchCursor(
        @AuthenticationPrincipal auth: UserAuthentication,
        @RequestParam(required = false) calligraphyId: UUID? = null,
        @PageableDefault(size = 10, page = 0) pageable: Pageable,
    ): ApiResponseEntity<ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>> {
        val query = toGetArchivedCalligraphiesCursorQuery(auth, calligraphyId, pageable)
        val response = archiveCalligraphyQueryUsecase.fetchMyArchivedCalligraphies(query)

        return success(response)
    }
}