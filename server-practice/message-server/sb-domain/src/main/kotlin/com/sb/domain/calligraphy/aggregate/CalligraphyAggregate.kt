package com.sb.domain.calligraphy.aggregate

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.*
import java.time.Instant

class CalligraphyAggregate private constructor(
    private val calligraphy: Calligraphy
) {
    val snapshot: Calligraphy get() = calligraphy

    companion object {
        fun restore(calligraphy: Calligraphy): CalligraphyAggregate = CalligraphyAggregate(calligraphy)

        fun create(
            text: Text,
            prompt: Prompt,
            style: StyleType,
            user: Author,
            result: CalligraphyResult,
            seed: Seed = Seed.generate(),
            now: Instant = Instant.now(),
        ): CalligraphyAggregate = CalligraphyAggregate(
            Calligraphy(
                id = generateCalligraphyId(now),
                text = text,
                seed = seed,
                prompt = prompt,
                style = style,
                user = user,
                result = result,
                createdAt = now,
                updatedAt = now,
            )
        )

        private fun generateCalligraphyId(now: Instant): Calligraphy.CalligraphyId =
            Calligraphy.CalligraphyId(now.toEpochMilli())
    }
}