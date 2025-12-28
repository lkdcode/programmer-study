package com.sb.application.common.dto

data class PageRequest(
    val page: Int,
    val size: Int,
    val sort: SortOptionList? = null,
) {
    enum class SortDirection {
        ASC,
        DESC
    }

    init {
        // TODO errorCode
        require(page >= 0) { "page must be >= 0" }
        require(size > 0) { "size must be > 0" }
    }
}

data class SortOptionList(
    val list: List<SortOption>
)

data class SortOption(
    val property: String,
    val direction: PageRequest.SortDirection = PageRequest.SortDirection.DESC
)