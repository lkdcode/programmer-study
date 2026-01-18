package com.sb.adapter.calligraphy.input.web.rest.query.request.mapper

import com.sb.adapter.common.input.web.page.toSortOptionList
import com.sb.application.calligraphy.dto.query.GetShowcaseFeedCursorQuery
import com.sb.application.calligraphy.dto.query.GetUserCalligraphiesCursorQuery
import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.entity.User
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.data.domain.Pageable
import java.util.UUID

fun toGetShowcaseFeedCursorQuery(
    auth: UserAuthentication?,
    calligraphyId: UUID?,
    pageable: Pageable,
): GetShowcaseFeedCursorQuery = GetShowcaseFeedCursorQuery(
    viewer = auth?.userIdVo,
    cursorPageRequest = CursorPageRequest(
        key = calligraphyId?.let { Calligraphy.CalligraphyId(it) },
        pageSize = pageable.pageSize,
        sort = pageable.toSortOptionList(),
    )
)

fun toGetUserCalligraphiesCursorQuery(
    auth: UserAuthentication?,
    authorUserId: Long,
    calligraphyId: UUID?,
    pageable: Pageable,
): GetUserCalligraphiesCursorQuery = GetUserCalligraphiesCursorQuery(
    author = Author(User.UserId(authorUserId)),
    viewer = auth?.userIdVo,
    cursorPageRequest = CursorPageRequest(
        key = calligraphyId?.let { Calligraphy.CalligraphyId(it) },
        pageSize = pageable.pageSize,
        sort = pageable.toSortOptionList(),
    )
)