package com.sb.domain.calligraphy.exception

import com.sb.domain.exception.DomainErrorCode

enum class CalligraphyErrorCode(
    override val code: String,
    override val message: String,
) : DomainErrorCode {
    PROMPT_REQUIRED("CAL001", "명령어는 필수입니다."),
    PROMPT_TOO_LONG("CAL002", "명령어 길이가 올바르지 않습니다."),
    SHOWCASE_SIZE_INVALID("CAL003", "쇼케이스 크기 값이 올바르지 않습니다."),
    SEED_INVALID("CAL004", "Seed는 6자리 난수여야 합니다."),
    RESULT_REQUIRED("CAL005", "캘리그래피 결과는 필수입니다."),
    PERMISSION_DENIED("CAL006", "캘리그래피 생성 권한이 없습니다."),

    ;
}