package com.sb.adapter.common.input.web.page

import com.sb.application.common.input.query.sort.SortDirection
import com.sb.application.common.input.query.sort.SortOption
import com.sb.application.common.input.query.sort.SortOptionList
import org.springframework.data.domain.Pageable

fun Pageable.toSortOptionList(): SortOptionList? =
    this.sort
        .filter { it.property.isNotBlank() }
        .map { order ->
            SortOption(
                order.property,
                if (order.direction.name == SortDirection.DESC.name) SortDirection.DESC else SortDirection.ASC
            )
        }
        .toList()
        .let { SortOptionList(it) }