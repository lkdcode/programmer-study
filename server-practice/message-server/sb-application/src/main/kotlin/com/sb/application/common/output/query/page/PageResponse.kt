package com.sb.application.common.output.query.page

import com.sb.application.common.exception.ApplicationPageErrorCode
import com.sb.application.exception.applicationRequire

data class PageResponse(
    val totalItems: Long,
    val totalPages: Int,
    val currentPage: Int,
    val perPage: Int,
) {

    init {
        applicationRequire(totalItems >= 0, ApplicationPageErrorCode.INVALID_TOTAL_ITEMS)
        applicationRequire(totalPages >= 0, ApplicationPageErrorCode.INVALID_TOTAL_PAGES)
        applicationRequire(currentPage >= 0, ApplicationPageErrorCode.INVALID_PAGE_INDEX)
        applicationRequire(perPage >= 0, ApplicationPageErrorCode.INVALID_PAGE_SIZE)
    }
}