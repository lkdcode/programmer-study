package com.sb.adapter.like.output.infrastructure.r2dbc.entity

import com.sb.framework.r2dbc.entity.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("mst_calligraphy_like")
data class CalligraphyLikeR2dbcEntity(
    @Id
    @Column("id")
    var id: Long? = null,

    @Column("calligraphy_id")
    var calligraphyId: UUID,

    @Column("user_id")
    var userId: Long,
) : BaseEntity()