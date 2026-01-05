package com.sb.application.feedback.dto.query

import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.domain.feedback.entity.CalligraphyFeedback
import com.sb.domain.user.entity.User

data class GetFeedbackPageQuery(
    val userId: User.UserId,
    val pageRequest: PageRequest,
)

data class GetFeedbackSliceQuery(
    val userId: User.UserId,
    val slicePageRequest: SlicePageRequest,
)

data class GetFeedbackCursorQuery(
    val userId: User.UserId,
    val cursorPageRequest: CursorPageRequest<CalligraphyFeedback.CalligraphyFeedbackId>,
)