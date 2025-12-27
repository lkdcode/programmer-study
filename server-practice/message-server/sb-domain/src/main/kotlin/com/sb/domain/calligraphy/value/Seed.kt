package com.sb.domain.calligraphy.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.calligraphy.exception.CalligraphyErrorCode
import kotlin.random.Random

@JvmInline
value class Seed private constructor(
    val value: String
) {
    init {
        domainRequire(value.length == 6 && value.all { it.isDigit() }, CalligraphyErrorCode.SEED_INVALID) { REQUIRE_MESSAGE }
    }

    companion object {
        const val REQUIRE_MESSAGE = "Seed는 6자리 숫자 문자열이어야 합니다."

        fun of(value: String): Seed = Seed(value)
        fun generate(): Seed =
            Seed(Random.Default.nextInt(0, 1_000_000).toString().padStart(6, '0'))
    }
}
