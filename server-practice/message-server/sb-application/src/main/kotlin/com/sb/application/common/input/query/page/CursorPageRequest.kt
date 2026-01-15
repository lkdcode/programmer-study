package com.sb.application.common.input.query.page

import com.sb.application.common.exception.ApplicationPageErrorCode
import com.sb.application.common.input.query.sort.SortOptionList
import com.sb.application.exception.applicationRequire

data class CursorPageRequest<T>(
    val key: T? = null,
    val pageSize: Int,
    val sort: SortOptionList? = null,
) {

    init {
        applicationRequire(pageSize > 0, ApplicationPageErrorCode.INVALID_PAGE_SIZE)
    }
}