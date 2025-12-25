package com.sb.domain.calligraphy.exception

import com.sb.domain.exception.DomainErrorCode

enum class CalligraphyErrorCode(
    override val code: String,
    override val message: String,
) : DomainErrorCode {

    TEXT_REQUIRED("CAL001", "작성할 문구는 필수입니다."),
    TEXT_TOO_LONG("CAL002", "문구의 길이가 올바르지 않습니다."),
    SHOWCASE_SIZE_INVALID("CAL003", "쇼케이스 크기 값이 올바르지 않습니다."),
    SEED_INVALID("CAL004", "Seed는 6자리 난수여야 합니다."),
    RESULT_REQUIRED("CAL005", "캘리그래피 결과는 필수입니다."),
    PERMISSION_DENIED("CAL006", "캘리그래피 생성 권한이 없습니다."),
    INSUFFICIENT_CREDIT("CAL007", "크레딧이 부족합니다."),
    AUTHOR_NOT_REGISTERED("CAL008", "회원가입된 사용자만 캘리그래피를 생성할 수 있습니다."),

    PROMPT_REQUIRED("CAL001", "작성할 문구는 필수입니다."),
    PROMPT_TOO_LONG("CAL002", "문구의 길이가 올바르지 않습니다."),


    ;
}