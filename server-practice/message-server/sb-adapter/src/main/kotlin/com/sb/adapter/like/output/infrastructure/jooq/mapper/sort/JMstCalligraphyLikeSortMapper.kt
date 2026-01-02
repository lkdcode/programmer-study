package com.sb.adapter.like.output.infrastructure.jooq.mapper.sort

import com.sb.application.common.input.query.sort.SortDirection
import com.sb.application.common.input.query.sort.SortOptionList
import com.sb.jooq.tables.references.JMST_CALLIGRAPHY_LIKE
import org.jooq.SortField


fun SortOptionList?.toCalligraphyLikeSortField(): List<SortField<*>>? =
    this
        ?.list
        ?.mapNotNull {
            when (it.property) {
                "id" -> {
                    if (it.direction == SortDirection.ASC) JMST_CALLIGRAPHY_LIKE.ID.asc()
                    else JMST_CALLIGRAPHY_LIKE.ID.desc()
                }

                "createdAt" -> {
                    if (it.direction == SortDirection.ASC) JMST_CALLIGRAPHY_LIKE.CREATED_AT.asc()
                    else JMST_CALLIGRAPHY_LIKE.CREATED_AT.desc()

                }

                else -> null
            }
        }
        ?.toList()