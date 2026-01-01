package com.sb.application.common.input.query.sort

data class SortOptionList(
    val list: List<SortOption>
)

data class SortOption(
    val property: String,
    val direction: SortDirection = SortDirection.DESC
)

enum class SortDirection {
    ASC,
    DESC
}