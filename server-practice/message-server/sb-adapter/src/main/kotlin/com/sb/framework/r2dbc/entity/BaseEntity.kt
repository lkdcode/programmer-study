package com.sb.framework.r2dbc.entity

import com.sb.adapter.common.domain.spec.RemarkSpec
import com.sb.framework.time.nowUTC
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import java.time.Instant

abstract class BaseEntity(
    @field:Size(max = RemarkSpec.MAX_LENGTH, message = RemarkSpec.INVALID_MAX_LENGTH_MESSAGE)
    @Column("remark1")
    var remark1: String? = null,

    @field:Size(max = RemarkSpec.MAX_LENGTH, message = RemarkSpec.INVALID_MAX_LENGTH_MESSAGE)
    @Column("remark2")
    var remark2: String? = null,

    @field:Size(max = RemarkSpec.MAX_LENGTH, message = RemarkSpec.INVALID_MAX_LENGTH_MESSAGE)
    @Column("remark3")
    var remark3: String? = null,

    @CreatedDate
    @Column("created_at")
    open var createdAt: Instant = nowUTC(),
) : Validatable {

    @LastModifiedDate
    @Column("updated_at")
    var updatedAt: Instant = nowUTC()

    @CreatedBy
    @Column("created_by")
    var createdBy: Long = 0

    @LastModifiedBy
    @Column("updated_by")
    var updatedBy: Long = 0

    @Column("is_deleted")
    private var isDeleted: Boolean = false

    @Column("deleted_at")
    private var deletedAt: Instant? = null

    open fun delete() {
        this.isDeleted = true
        this.deletedAt = nowUTC()
    }

    fun restore() {
        this.isDeleted = false
    }

    fun isNotDeleted(): Boolean = !isDeleted
    fun isDeleted(): Boolean = isDeleted
}