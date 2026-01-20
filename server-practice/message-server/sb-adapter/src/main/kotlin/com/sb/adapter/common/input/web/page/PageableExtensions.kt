package com.sb.adapter.common.input.web.page

import com.sb.application.common.input.query.sort.SortDirection
import com.sb.application.common.input.query.sort.SortOption
import com.sb.application.common.input.query.sort.SortOptionList
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

fun Pageable.toSortOptionList(): SortOptionList {
    val options = sort.map { order ->
        SortOption(
            property = order.property,
            direction = order.direction.toSortDirection()
        )
    }.toList()

    return SortOptionList(options)
}

private fun Sort.Direction.toSortDirection(): SortDirection = when (this) {
    Sort.Direction.ASC -> SortDirection.ASC
    Sort.Direction.DESC -> SortDirection.DESC
}
