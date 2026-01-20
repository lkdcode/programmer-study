package com.sb.adapter.calligraphy.output.infrastructure.jooq.mapper.sort

import com.sb.application.common.input.query.sort.SortOptionList
import com.sb.framework.jooq.mapper.sort.sortOption
import com.sb.jooq.tables.references.JMST_CALLIGRAPHY
import org.jooq.SortField

private val DEFAULT_SORT = JMST_CALLIGRAPHY.CREATED_AT.desc()

private val AVAILABLE_PROPERTY = mapOf(
    "id" to JMST_CALLIGRAPHY.ID,
    "createdAt" to JMST_CALLIGRAPHY.CREATED_AT,
)

fun SortOptionList?.toCalligraphySortField(): List<SortField<*>> =
    this
        ?.list
        .orEmpty()
        .mapNotNull {
            AVAILABLE_PROPERTY[it.property]
                ?.sortOption(it.direction)
        }
        .ifEmpty { listOf(DEFAULT_SORT) }