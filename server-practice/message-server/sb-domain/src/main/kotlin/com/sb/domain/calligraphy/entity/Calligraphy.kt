package com.sb.domain.calligraphy.entity

import com.sb.domain.calligraphy.value.*
import java.time.Instant

data class Calligraphy(
    val id: CalligraphyId,

    val seed: Seed,
    val text: Text,
    val prompt: Prompt,

    val style: StyleType,
    val user: Author,
    val result: CalligraphyResult,

    val createdAt: Instant,
    val updatedAt: Instant,
) {

    @JvmInline
    value class CalligraphyId(val value: Long)
}