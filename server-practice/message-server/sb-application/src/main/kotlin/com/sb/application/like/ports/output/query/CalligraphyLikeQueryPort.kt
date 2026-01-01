package com.sb.application.like.ports.output.query

import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyPage
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphySlice
import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.domain.calligraphy.value.Author

interface CalligraphyLikeQueryPort {
    suspend fun findLikedByUser(user: Author, pageRequest: PageRequest): ShowcaseCalligraphyPage
    suspend fun findLikedByUser(user: Author, pageRequest: SlicePageRequest): ShowcaseCalligraphySlice
    suspend fun findLikedByUser(user: Author, pageRequest: CursorPageRequest): ShowcaseCalligraphyCursor
}