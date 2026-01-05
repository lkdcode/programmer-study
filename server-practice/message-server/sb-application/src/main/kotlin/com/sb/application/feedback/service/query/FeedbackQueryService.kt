package com.sb.application.feedback.service.query

import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.application.feedback.dto.query.FeedbackCalligraphyCursor
import com.sb.application.feedback.dto.query.FeedbackCalligraphyPage
import com.sb.application.feedback.dto.query.FeedbackCalligraphySlice
import com.sb.application.feedback.dto.query.GetFeedbackCursorQuery
import com.sb.application.feedback.dto.query.GetFeedbackPageQuery
import com.sb.application.feedback.dto.query.GetFeedbackSliceQuery
import com.sb.application.feedback.ports.input.query.FeedbackQueryUsecase
import com.sb.application.feedback.ports.output.query.CalligraphyFeedbackQueryPort
import com.sb.domain.feedback.entity.CalligraphyFeedback
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FeedbackQueryService(
    private val queryPort: CalligraphyFeedbackQueryPort,
) : FeedbackQueryUsecase {

    override suspend fun fetchMyFeedbackList(
        query: GetFeedbackPageQuery,
        pageRequest: PageRequest
    ): List<FeedbackCalligraphyPage> =
        queryPort.fetchFeedbackByUser(query, pageRequest)

    override suspend fun fetchMyFeedbackList(
        query: GetFeedbackSliceQuery,
        pageRequest: SlicePageRequest
    ): List<FeedbackCalligraphySlice> =
        queryPort.fetchFeedbackByUser(query, pageRequest)

    override suspend fun fetchMyFeedbackList(
        query: GetFeedbackCursorQuery,
        pageRequest: CursorPageRequest<CalligraphyFeedback.CalligraphyFeedbackId>
    ): List<FeedbackCalligraphyCursor> =
        queryPort.fetchFeedbackByUser(query, pageRequest)
}