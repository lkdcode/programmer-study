package com.sb.application.common.input.query.page

import com.sb.application.common.exception.ApplicationPageErrorCode
import com.sb.application.common.input.query.sort.SortOptionList
import com.sb.application.exception.applicationRequire

data class CursorPageRequest(
    val key: String? = null,
    val size: Int,
    val sort: SortOptionList? = null,
) {

    init {
        applicationRequire(size > 0, ApplicationPageErrorCode.INVALID_PAGE_SIZE)
    }
}