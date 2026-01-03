package com.sb.adapter.feedback.output.infrastructure.r2dbc.repository

import com.sb.adapter.feedback.output.infrastructure.r2dbc.entity.CalligraphyFeedbackR2dbcEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface CalligraphyFeedbackR2dbcRepository : R2dbcRepository<CalligraphyFeedbackR2dbcEntity, Long>