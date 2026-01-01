package com.sb.application.common.exception

import com.sb.application.exception.ApplicationErrorCode

enum class ApplicationPageErrorCode(
    override val code: String,
    override val message: String,
) : ApplicationErrorCode {
    INVALID_PAGE_INDEX("PGE001", "페이지 번호가 올바르지 않습니다."),
    INVALID_PAGE_SIZE("PGE002", "페이지 크기가 올바르지 않습니다."),
    INVALID_TOTAL_ITEMS("PGE003", "전체 아이템 수가 올바르지 않습니다."),
    INVALID_TOTAL_PAGES("PGE004", "전체 페이지 수가 올바르지 않습니다."),
}