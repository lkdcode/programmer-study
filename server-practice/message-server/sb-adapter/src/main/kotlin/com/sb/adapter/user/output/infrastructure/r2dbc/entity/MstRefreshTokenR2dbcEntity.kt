package com.sb.adapter.user.output.infrastructure.r2dbc.entity

import com.sb.framework.r2dbc.entity.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("mst_refresh_token")
data class MstRefreshTokenR2dbcEntity(
    @Id
    @Column("id")
    var id: Long? = null,

    @Column("user_id")
    var userId: Long,

    @Column("jwt")
    var jwt: String,

    @Column("expired_at")
    var expiredAt: Instant,
) : BaseEntity()