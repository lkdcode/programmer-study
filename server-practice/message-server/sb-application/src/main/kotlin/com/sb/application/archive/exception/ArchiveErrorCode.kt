package com.sb.application.archive.exception

import com.sb.application.exception.ApplicationErrorCode

enum class ArchiveErrorCode(
    override val code: String,
    override val message: String,
) : ApplicationErrorCode {
    ALREADY_ARCHIVED("ARC001", "이미 보관한 캘리그래피입니다."),
    NOT_ARCHIVED("ARC002", "보관하지 않은 캘리그래피입니다."),
}
