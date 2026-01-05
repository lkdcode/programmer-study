package com.sb.application.feedback.ports.output.query

import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.application.feedback.dto.query.*
import com.sb.domain.feedback.entity.CalligraphyFeedback

interface CalligraphyFeedbackQueryPort {

    suspend fun fetchFeedbackByUser(
        query: GetFeedbackPageQuery,
        pageRequest: PageRequest
    ): List<FeedbackCalligraphyPage>

    suspend fun fetchFeedbackByUser(
        query: GetFeedbackSliceQuery,
        pageRequest: SlicePageRequest
    ): List<FeedbackCalligraphySlice>

    suspend fun fetchFeedbackByUser(
        query: GetFeedbackCursorQuery,
        pageRequest: CursorPageRequest<CalligraphyFeedback.CalligraphyFeedbackId>
    ): List<FeedbackCalligraphyCursor>
}