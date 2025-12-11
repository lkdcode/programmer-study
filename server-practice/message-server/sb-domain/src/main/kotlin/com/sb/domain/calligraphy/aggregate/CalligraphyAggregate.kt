package com.sb.domain.calligraphy.aggregate

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.CalligraphyResult
import com.sb.domain.calligraphy.value.Prompt
import com.sb.domain.calligraphy.value.Seed
import com.sb.domain.calligraphy.value.StyleType
import com.sb.domain.calligraphy.value.User
import java.time.Instant

class CalligraphyAggregate private constructor(
    private val calligraphy: Calligraphy
) {
    val getCalligraphy: Calligraphy get() = calligraphy

    fun revise(
        additionalPrompt: Prompt,
        newStyle: StyleType,
        newResult: CalligraphyResult,
        newSeed: Seed,
        requester: User,
    ): CalligraphyAggregate {
        val now = Instant.now()
        val next = calligraphy.createNext(
            additionalPrompt = additionalPrompt,
            newStyle = newStyle,
            newResult = newResult,
            newSeed = newSeed,
            requester = requester,
            newId = generateCalligraphyId(),
            now = now,
        )

        return CalligraphyAggregate(next)
    }

    companion object {
        fun create(
            prompt: Prompt,
            style: StyleType,
            user: User,
            result: CalligraphyResult,
            seed: Seed = Seed.generate(),
        ): CalligraphyAggregate = CalligraphyAggregate(
            Calligraphy(
                id = generateCalligraphyId(),
                seed = seed,
                prompt = prompt,
                style = style,
                user = user,
                result = result,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
            )
        )

        private fun generateCalligraphyId(): Calligraphy.CalligraphyId =
            Calligraphy.CalligraphyId(Instant.now().toEpochMilli())
    }
}

