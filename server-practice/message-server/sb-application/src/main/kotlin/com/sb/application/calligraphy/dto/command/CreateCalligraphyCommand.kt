package com.sb.application.calligraphy.dto.command

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.*

data class CreateCalligraphyCommand(
    val id: Calligraphy.CalligraphyId,
    val seed: Seed,
    val text: Text,
    val prompt: Prompt?,
    val style: StyleType,
    val author: Author,
)