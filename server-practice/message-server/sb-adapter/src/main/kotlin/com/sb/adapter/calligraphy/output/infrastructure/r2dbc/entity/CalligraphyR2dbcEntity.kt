package com.sb.adapter.calligraphy.output.infrastructure.r2dbc.entity

import com.sb.domain.calligraphy.value.StyleType
import com.sb.framework.r2dbc.entity.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("mst_calligraphy")
data class CalligraphyR2dbcEntity(
    @Id
    @Column("id")
    var calligraphyId: UUID,

    @Column("seed")
    var seed: String,

    @Column("text")
    var text: String,

    @Column("prompt")
    var prompt: String?,

    @Column("style")
    var style: StyleType,

    @Column("user_id")
    var userId: Long,

    @Column("path")
    var path: String,
) : BaseEntity(), Persistable<UUID> {
    override fun getId(): UUID? = calligraphyId

    override fun isNew(): Boolean = true
}