package com.sb.application.calligraphy.dto.query

import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.domain.calligraphy.value.Author

data class GetLikedCalligraphiesPageQuery(
    val user: Author,
    val pageRequest: PageRequest
)

data class GetLikedCalligraphiesSliceQuery(
    val user: Author,
    val slicePageRequest: SlicePageRequest
)

data class GetLikedCalligraphiesCursorQuery(
    val user: Author,
    val cursorPageRequest: CursorPageRequest
)