package com.sb.application.common.output.query.page

import com.sb.application.common.exception.ApplicationPageErrorCode
import com.sb.application.exception.applicationRequire

data class SlicePageResponse(
    val pageNumber: Long,
    val pageSize: Int,
    val hasNext: Boolean
) {

    init {
        applicationRequire(pageNumber >= 0, ApplicationPageErrorCode.INVALID_PAGE_INDEX)
        applicationRequire(pageSize > 0, ApplicationPageErrorCode.INVALID_PAGE_SIZE)
    }
}