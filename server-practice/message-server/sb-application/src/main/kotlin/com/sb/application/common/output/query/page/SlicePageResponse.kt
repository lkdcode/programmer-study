package com.sb.application.common.output.query.page

import com.sb.application.common.exception.ApplicationPageErrorCode
import com.sb.application.exception.applicationRequire

data class SlicePageResponse(
    val page: Long,
    val size: Int,
    val hasNext: Boolean
) {

    init {
        applicationRequire(page >= 0, ApplicationPageErrorCode.INVALID_PAGE_INDEX)
        applicationRequire(size > 0, ApplicationPageErrorCode.INVALID_PAGE_SIZE)
    }
}