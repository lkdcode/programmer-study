package com.sb.adapter.plan.output.infrastructure.r2dbc.entity

import com.sb.framework.r2dbc.entity.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("mst_credit_wallet")
data class CreditWalletR2dbcEntity(
    @Id
    @Column("id")
    var id: Long? = null,

    @Column("user_id")
    var userId: Long,

    @Column("balance")
    var balance: Long,
) : BaseEntity()