package com.sb.application.calligraphy.dto.query

import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.output.query.page.CursorPageResponse
import com.sb.application.common.output.query.page.SlicePageResponse
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.*
import java.time.Instant


data class ShowcaseCalligraphyPage(
    val items: List<ShowcaseCalligraphy>,
    val pageRequest: PageRequest,
)

data class ShowcaseCalligraphySlice(
    val items: List<ShowcaseCalligraphy>,
    val slicePageResponse: SlicePageResponse,
)

data class ShowcaseCalligraphyCursor(
    val items: List<ShowcaseCalligraphy>,
    val cursorPageResponse: CursorPageResponse,
)

data class ShowcaseCalligraphy(
    val id: Calligraphy.CalligraphyId,
    val user: Author,
    val text: Text,
    val prompt: Prompt?,
    val style: StyleType,
    val seed: Seed,
    val result: String,
    val createdAt: Instant,
    val isLike: Boolean,
    val likeCount: Int,
)