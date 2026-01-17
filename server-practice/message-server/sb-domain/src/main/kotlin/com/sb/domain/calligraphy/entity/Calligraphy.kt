package com.sb.domain.calligraphy.entity

import com.sb.domain.calligraphy.value.*
import java.util.*

data class Calligraphy(
    val id: CalligraphyId,
    val seed: Seed,
    val text: Text,
    val prompt: Prompt?,
    val style: StyleType,
    val author: Author,
) {

    @JvmInline
    value class CalligraphyId(val value: UUID)
}