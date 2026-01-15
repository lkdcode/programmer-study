package com.sb.application.common.input.query.page

import com.sb.application.common.exception.ApplicationPageErrorCode
import com.sb.application.common.input.query.sort.SortOptionList
import com.sb.application.exception.applicationRequire

data class PageRequest(
    val pageNumber: Long,
    val pageSize: Int,
    val sort: SortOptionList? = null,
) {
    val offset: Long get() = (pageNumber * pageSize)

    init {
        applicationRequire(pageNumber >= 0, ApplicationPageErrorCode.INVALID_PAGE_INDEX)
        applicationRequire(pageSize > 0, ApplicationPageErrorCode.INVALID_PAGE_SIZE)
    }
}