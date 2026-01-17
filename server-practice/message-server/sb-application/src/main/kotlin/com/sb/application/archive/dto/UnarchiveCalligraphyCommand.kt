package com.sb.application.archive.dto

import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author

data class UnarchiveCalligraphyCommand(
    val calligraphyId: Calligraphy.CalligraphyId,
    val user: Author,
)