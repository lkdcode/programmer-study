package com.sb.application.archive.dto.query

import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author

data class GetArchivedCalligraphiesPageQuery(
    val user: Author,
    val pageRequest: PageRequest
)

data class GetArchivedCalligraphiesSliceQuery(
    val user: Author,
    val slicePageRequest: SlicePageRequest
)

data class GetArchivedCalligraphiesCursorQuery(
    val user: Author,
    val cursorPageRequest: CursorPageRequest<Calligraphy.CalligraphyId>
)