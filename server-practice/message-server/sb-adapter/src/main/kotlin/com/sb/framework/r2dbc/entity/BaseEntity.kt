package com.sb.framework.r2dbc.entity

import com.sb.framework.time.nowUTC
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import java.time.Instant

abstract class BaseEntity(
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