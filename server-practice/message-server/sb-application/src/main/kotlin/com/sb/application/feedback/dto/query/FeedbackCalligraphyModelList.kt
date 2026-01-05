package com.sb.application.feedback.dto.query

import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphy
import com.sb.application.common.output.query.page.CursorPageResponse
import com.sb.application.common.output.query.page.PageResponse
import com.sb.application.common.output.query.page.SlicePageResponse
import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.domain.feedback.value.FeedbackContent

data class FeedbackCalligraphyPage(
    val items: List<FeedbackCalligraphyModel>,
    val pageResponse: PageResponse,
)

data class FeedbackCalligraphySlice(
    val items: List<FeedbackCalligraphyModel>,
    val slicePageResponse: SlicePageResponse,
)

data class FeedbackCalligraphyCursor(
    val items: List<FeedbackCalligraphyModel>,
    val cursorPageResponse: CursorPageResponse<CalligraphyFeedback.CalligraphyFeedbackId>,
)

data class FeedbackCalligraphyModel(
    val feedbackId: CalligraphyFeedback.CalligraphyFeedbackId,
    val content: FeedbackContent,
    val showcaseCalligraphy: ShowcaseCalligraphy,
)