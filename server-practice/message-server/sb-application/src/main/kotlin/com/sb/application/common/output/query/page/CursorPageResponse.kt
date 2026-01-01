package com.sb.application.common.output.query.page

import com.sb.application.common.exception.ApplicationPageErrorCode
import com.sb.application.exception.applicationRequire

data class CursorPageResponse(
    val nextKey: String? = null,
    val size: Int,
) {

    init {
        applicationRequire(size > 0, ApplicationPageErrorCode.INVALID_PAGE_SIZE)
    }
}