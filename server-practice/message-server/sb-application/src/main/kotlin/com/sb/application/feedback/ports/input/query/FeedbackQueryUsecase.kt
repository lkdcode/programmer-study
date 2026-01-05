package com.sb.application.feedback.ports.input.query

import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.application.feedback.dto.query.*
import com.sb.domain.feedback.entity.CalligraphyFeedback

interface FeedbackQueryUsecase {

    suspend fun fetchMyFeedbackList(
        query: GetFeedbackPageQuery,
        pageRequest: PageRequest
    ): List<FeedbackCalligraphyPage>

    suspend fun fetchMyFeedbackList(
        query: GetFeedbackSliceQuery,
        pageRequest: SlicePageRequest
    ): List<FeedbackCalligraphySlice>

    suspend fun fetchMyFeedbackList(
        query: GetFeedbackCursorQuery,
        pageRequest: CursorPageRequest<CalligraphyFeedback.CalligraphyFeedbackId>
    ): List<FeedbackCalligraphyCursor>
}