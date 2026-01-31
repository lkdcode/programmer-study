package com.sb.adapter.plan.output.infrastructure.r2dbc.entity

import com.sb.framework.r2dbc.entity.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("mst_credit_tx")
data class CreditTransactionR2dbcEntity(
    @Id
    @Column("id")
    var txId: UUID,

    @Column("user_id")
    var userId: Long,

    @Column("type")
    var type: String,

    @Column("amount")
    var amount: Long,

    @Column("reason")
    var reason: String,
) : BaseEntity(), Persistable<UUID> {
    override fun getId(): UUID? = txId

    override fun isNew(): Boolean = true
}