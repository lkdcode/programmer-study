package com.sb.adapter.feedback.output.infrastructure.r2dbc.entity

import com.sb.framework.r2dbc.entity.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("mst_calligraphy_feedback")
data class CalligraphyFeedbackR2dbcEntity(
    @Id
    @Column("id")
    var id: Long? = null,

    @Column("calligraphy_id")
    var calligraphyId: UUID,

    @Column("user_id")
    var userId: Long,

    @Column("content")
    var content: String,
) : BaseEntity()