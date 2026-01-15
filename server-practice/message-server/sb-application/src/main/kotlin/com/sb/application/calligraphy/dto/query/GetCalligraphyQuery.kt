package com.sb.application.calligraphy.dto.query

import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.entity.User

data class GetShowcaseFeedPageQuery(
    val viewer: User.UserId?,
    val pageRequest: PageRequest,
)

data class GetShowcaseFeedSliceQuery(
    val viewer: User.UserId?,
    val slicePageRequest: SlicePageRequest,
)

data class GetShowcaseFeedCursorQuery(
    val viewer: User.UserId?,
    val cursorPageRequest: CursorPageRequest<Calligraphy.CalligraphyId>,
)

data class GetUserCalligraphiesPageQuery(
    val author: Author,
    val viewer: User.UserId?,
    val pageRequest: PageRequest,
)

data class GetUserCalligraphiesSliceQuery(
    val author: Author,
    val viewer: User.UserId?,
    val slicePageRequest: SlicePageRequest,
)

data class GetUserCalligraphiesCursorQuery(
    val author: Author,
    val viewer: User.UserId?,
    val cursorPageRequest: CursorPageRequest<Calligraphy.CalligraphyId>,
)
