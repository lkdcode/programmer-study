package com.sb.adapter.like.input.web.rest.query.request.mapper

import com.sb.adapter.common.input.toSortOptionList
import com.sb.application.calligraphy.dto.query.GetLikedCalligraphiesCursorQuery
import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.data.domain.Pageable


fun toGetLikedCalligraphiesCursorQuery(
    auth: UserAuthentication,
    calligraphyId: Long? = null,
    pageable: Pageable,
): GetLikedCalligraphiesCursorQuery = GetLikedCalligraphiesCursorQuery(
    auth.authorVo,
    CursorPageRequest<Calligraphy.CalligraphyId>(
        calligraphyId?.let { Calligraphy.CalligraphyId(it) },
        pageable.pageSize,
        pageable.toSortOptionList(),
    )
)