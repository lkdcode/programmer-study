package com.sb.adapter.like.input.web.rest.query.request.mapper

import com.sb.adapter.common.input.web.page.toSortOptionList
import com.sb.application.like.dto.query.GetLikedCalligraphiesCursorQuery
import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.data.domain.Pageable
import java.util.UUID


fun toGetLikedCalligraphiesCursorQuery(
    auth: UserAuthentication,
    calligraphyId: UUID? = null,
    pageable: Pageable,
): GetLikedCalligraphiesCursorQuery = GetLikedCalligraphiesCursorQuery(
    auth.authorVo,
    CursorPageRequest<Calligraphy.CalligraphyId>(
        calligraphyId?.let { Calligraphy.CalligraphyId(it) },
        pageable.pageSize,
        pageable.toSortOptionList(),
    )
)