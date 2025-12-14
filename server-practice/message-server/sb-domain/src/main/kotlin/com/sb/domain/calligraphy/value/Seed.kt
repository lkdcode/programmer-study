package com.sb.domain.calligraphy.value

import com.sb.domain.exception.domainRequire
import com.sb.domain.calligraphy.exception.CalligraphyErrorCode
import kotlin.random.Random

@JvmInline
value class Seed private constructor(
    val value: Long
) {
    init {
        domainRequire(value in MIN_VALUE..MAX_VALUE, CalligraphyErrorCode.SEED_INVALID) { REQUIRE_MESSAGE }
    }

    companion object {
        const val MIN_VALUE: Long = 100_000
        const val MAX_VALUE: Long = 999_999
        const val REQUIRE_MESSAGE = "Seed는 6자리 난수($MIN_VALUE~$MAX_VALUE)여야 합니다."

        fun of(value: Long): Seed = Seed(value)
        fun generate(): Seed =
            Seed(Random.Default.nextInt(MIN_VALUE.toInt(), (MAX_VALUE + 1).toInt()).toLong())
    }
}