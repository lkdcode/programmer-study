package com.sb.domain.calligraphy.command

import com.sb.domain.calligraphy.value.*

data class CreateCalligraphyCommand(
    val seed: Seed,
    val text: Text,
    val prompt: Prompt?,
    val style: StyleType,
    val author: Author,
)