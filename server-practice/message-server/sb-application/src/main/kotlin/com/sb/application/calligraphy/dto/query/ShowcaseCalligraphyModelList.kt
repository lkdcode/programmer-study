package com.sb.application.calligraphy.dto.query

import com.sb.application.common.output.query.page.CursorPageResponse
import com.sb.application.common.output.query.page.PageResponse
import com.sb.application.common.output.query.page.SlicePageResponse
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.*
import com.sb.domain.user.value.Nickname
import java.time.Instant


data class ShowcaseCalligraphyPage(
    val items: List<ShowcaseCalligraphy>,
    val pageResponse: PageResponse,
)

data class ShowcaseCalligraphySlice(
    val items: List<ShowcaseCalligraphy>,
    val slicePageResponse: SlicePageResponse,
)

data class ShowcaseCalligraphyCursor<T>(
    val items: List<ShowcaseCalligraphy>,
    val cursorPageResponse: CursorPageResponse<T>,
)

data class ShowcaseCalligraphy(
    val id: Calligraphy.CalligraphyId,
    val user: Author,
    val authorNickname: Nickname,
    val profileImage: String?,
    val text: Text,
    val prompt: Prompt?,
    val style: StyleType,
    val seed: Seed,
    val result: String,
    val createdAt: Instant,
    val isLike: Boolean,
    val likeCount: Int,
)