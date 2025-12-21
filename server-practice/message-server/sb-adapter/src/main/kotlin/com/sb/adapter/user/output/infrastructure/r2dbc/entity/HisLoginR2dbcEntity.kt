package com.sb.adapter.user.output.infrastructure.r2dbc.entity

import com.sb.framework.r2dbc.entity.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("his_login_user")
data class HisLoginR2dbcEntity(
    @Id
    @Column("id")
    var id: Long? = null,

    @Column("user_id")
    var userId: Long,

    @Column("email")
    var email: String,

    @Column("login_at")
    var loginAt: Instant,

    @Column("ip")
    var ip: String,

    @Column("agent")
    var agent: String?,
) : BaseEntity() {

}