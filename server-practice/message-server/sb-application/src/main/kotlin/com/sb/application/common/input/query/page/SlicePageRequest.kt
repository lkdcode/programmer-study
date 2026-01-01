package com.sb.application.common.input.query.page

import com.sb.application.common.exception.ApplicationPageErrorCode
import com.sb.application.common.input.query.sort.SortOptionList
import com.sb.application.exception.applicationRequire

data class SlicePageRequest(
    val page: Long,
    val size: Int,
    val sort: SortOptionList? = null,
) {
    val offset: Long get() = (page * size)

    init {
        applicationRequire(page >= 0, ApplicationPageErrorCode.INVALID_PAGE_INDEX)
        applicationRequire(size > 0, ApplicationPageErrorCode.INVALID_PAGE_SIZE)
    }
}