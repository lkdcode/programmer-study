package com.sb.adapter.feedback.output.infrastructure.r2dbc.mapper.entity

import com.sb.adapter.feedback.output.infrastructure.r2dbc.entity.CalligraphyFeedbackR2dbcEntity
import com.sb.adapter.feedback.output.infrastructure.r2dbc.mapper.vo.calligraphyFeedbackIdVo
import com.sb.adapter.feedback.output.infrastructure.r2dbc.mapper.vo.calligraphyIdVo
import com.sb.adapter.feedback.output.infrastructure.r2dbc.mapper.vo.feedbackContentVo
import com.sb.adapter.feedback.output.infrastructure.r2dbc.mapper.vo.userIdVo
import com.sb.application.feedback.dto.command.CreateFeedbackCommand
import com.sb.domain.feedback.aggregate.CalligraphyFeedbackAggregate

fun CreateFeedbackCommand.toEntity(): CalligraphyFeedbackR2dbcEntity =
    CalligraphyFeedbackR2dbcEntity(
        calligraphyId = this.calligraphyId.value,
        userId = this.userId.value,
        content = this.content.value,
    )

fun CalligraphyFeedbackR2dbcEntity.toAggregate(): CalligraphyFeedbackAggregate =
    CalligraphyFeedbackAggregate.create(
        id = this.calligraphyFeedbackIdVo,
        calligraphyId = this.calligraphyIdVo,
        userId = this.userIdVo,
        content = this.feedbackContentVo,
    )