package com.sb.domain.archive.entity

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import java.time.Instant

data class CalligraphyArchive(
    val id: CalligraphyArchiveId,
    val calligraphyId: Calligraphy.CalligraphyId,
    val user: Author,
    val createdAt: Instant,
) {
    @JvmInline
    value class CalligraphyArchiveId(val value: Long)
}