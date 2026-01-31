package com.sb.adapter.plan.output.infrastructure.r2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("mst_payment_order")
data class PaymentOrderR2dbcEntity(
    @Id
    @Column("id")
    val id: Long,

    @Column("user_id")
    val userId: Long,

    @Column("provider")
    val provider: String,

    @Column("method_ref")
    val methodRef: String?,

    @Column("amount")
    val amount: BigDecimal,

    @Column("currency")
    val currency: String,

    @Column("credits_to_grant")
    val creditsToGrant: Long,

    @Column("status")
    val status: String,

    @Column("provider_payment_id")
    val providerPaymentId: String?,

    @Column("created_at")
    val createdAt: Instant,

    @Column("updated_at")
    val updatedAt: Instant,
)