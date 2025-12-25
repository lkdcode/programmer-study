package com.sb.domain.calligraphy.command

import com.sb.domain.calligraphy.value.*
import java.time.Instant

data class CreateCalligraphyCommand(
    val seed: Seed,
    val text: Text,
    val prompt: Prompt,
    val style: StyleType,
    val author: Author,
) {
    val createdAt: Instant = Instant.now()
}