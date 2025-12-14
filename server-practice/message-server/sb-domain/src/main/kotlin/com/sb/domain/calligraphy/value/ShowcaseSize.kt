package com.sb.domain.calligraphy.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.calligraphy.exception.CalligraphyErrorCode

@JvmInline
value class ShowcaseSize private constructor(
    val value: Int
) {
    init {
        domainRequire(value in MIN..MAX, CalligraphyErrorCode.SHOWCASE_SIZE_INVALID) { REQUIRE_MESSAGE }
    }

    companion object {
        const val MIN = 1
        const val MAX = 50
        const val REQUIRE_MESSAGE = "전시 요청 개수는 ${MIN}~${MAX} 사이여야 합니다."

        fun of(value: Int): ShowcaseSize = ShowcaseSize(value)
    }
}