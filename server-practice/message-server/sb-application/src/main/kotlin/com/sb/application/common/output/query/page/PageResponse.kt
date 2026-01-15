package com.sb.application.common.output.query.page

import com.sb.application.common.exception.ApplicationPageErrorCode
import com.sb.application.exception.applicationRequire
import kotlin.math.ceil

class PageResponse private constructor(
    val totalElements: Long,
    val totalPages: Int,
    val pageNumber: Long,
    val pageSize: Int,
) {
    companion object {
        fun of(
            totalElements: Long,
            pageNumber: Long,
            pageSize: Int,
        ): PageResponse {
            applicationRequire(totalElements >= 0, ApplicationPageErrorCode.INVALID_TOTAL_ITEMS)
            applicationRequire(pageNumber >= 0, ApplicationPageErrorCode.INVALID_PAGE_INDEX)
            applicationRequire(pageSize > 0, ApplicationPageErrorCode.INVALID_PAGE_SIZE)

            return PageResponse(
                totalElements = totalElements,
                pageNumber = pageNumber,
                pageSize = pageSize,
                totalPages = ceil(totalElements.toDouble() / pageSize.toDouble()).toInt(),
            )
        }
    }
}