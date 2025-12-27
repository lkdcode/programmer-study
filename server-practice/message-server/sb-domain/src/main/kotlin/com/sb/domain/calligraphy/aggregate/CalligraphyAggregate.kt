package com.sb.domain.calligraphy.aggregate

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.*

class CalligraphyAggregate private constructor(
    private val calligraphy: Calligraphy
) {
    val snapshot: Calligraphy get() = calligraphy

    companion object {
        fun restore(calligraphy: Calligraphy): CalligraphyAggregate = CalligraphyAggregate(calligraphy)

        fun create(
            id: Calligraphy.CalligraphyId,
            seed: Seed,
            text: Text,
            prompt: Prompt?,
            style: StyleType,
            user: Author,
        ): CalligraphyAggregate = CalligraphyAggregate(
            Calligraphy(
                id = id,
                seed = seed,
                text = text,
                prompt = prompt,
                style = style,
                author = user,
            )
        )
    }
}