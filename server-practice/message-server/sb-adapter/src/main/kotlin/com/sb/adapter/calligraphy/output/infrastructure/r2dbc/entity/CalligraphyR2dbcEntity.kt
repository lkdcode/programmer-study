package com.sb.adapter.calligraphy.output.infrastructure.r2dbc.entity

import com.sb.domain.calligraphy.value.StyleType
import com.sb.framework.r2dbc.entity.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("mst_calligraphy")
data class CalligraphyR2dbcEntity(
    @Id
    @Column("id")
    val id: Long? = null,

    @Column("seed")
    val seed: String,

    @Column("text")
    val text: String,

    @Column("prompt")
    val prompt: String?,

    @Column("style")
    val style: StyleType,

    @Column("user_id")
    val userId: Long,
) : BaseEntity() {
}