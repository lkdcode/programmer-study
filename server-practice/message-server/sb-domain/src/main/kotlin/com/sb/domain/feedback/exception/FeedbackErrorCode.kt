package com.sb.domain.feedback.exception

import com.sb.domain.exception.DomainErrorCode

enum class FeedbackErrorCode(
    override val code: String,
    override val message: String,
) : DomainErrorCode {

    CONTENT_REQUIRED("FDB001", "피드백은 필수입니다."),
    CONTENT_TOO_LONG("FDB002", "피드백은 최대 길이를 초과할 수 없습니다."),
    ;
}