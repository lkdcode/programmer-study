package com.sb.domain.calligraphy.model

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Prompt
import com.sb.domain.calligraphy.value.Seed
import com.sb.domain.calligraphy.value.StyleType
import com.sb.domain.calligraphy.value.User
import java.time.Instant

data class ShowcaseCalligraphy(
    val id: Calligraphy.CalligraphyId,
    val user: User,
    val prompt: Prompt,
    val style: StyleType,
    val seed: Seed,
    val result: String,
    val createdAt: Instant,
)