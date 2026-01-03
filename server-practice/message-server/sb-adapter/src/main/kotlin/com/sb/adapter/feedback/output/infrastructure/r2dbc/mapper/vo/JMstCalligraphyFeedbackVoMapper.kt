package com.sb.adapter.feedback.output.infrastructure.r2dbc.mapper.vo

import com.sb.adapter.feedback.output.infrastructure.r2dbc.entity.CalligraphyFeedbackR2dbcEntity
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.feedback.entity.CalligraphyFeedback.CalligraphyFeedbackId
import com.sb.domain.feedback.value.FeedbackContent
import com.sb.domain.user.entity.User

val CalligraphyFeedbackR2dbcEntity.calligraphyFeedbackIdVo
    get() = CalligraphyFeedbackId(this.id!!)

val CalligraphyFeedbackR2dbcEntity.calligraphyIdVo
    get() = Calligraphy.CalligraphyId(this.calligraphyId)

val CalligraphyFeedbackR2dbcEntity.userIdVo
    get() = User.UserId(this.userId)

val CalligraphyFeedbackR2dbcEntity.feedbackContentVo
    get() = FeedbackContent.of(this.content)
