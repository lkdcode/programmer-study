package com.sb.application.common.output.query.page

data class CursorPageResponse<T>(
    val nextKey: T? = null,
)