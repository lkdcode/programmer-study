package com.sb.application.like.ports.output.query

import com.sb.application.common.dto.PageRequest
import com.sb.domain.calligraphy.model.ShowcaseCalligraphy
import com.sb.domain.calligraphy.value.Author

interface CalligraphyLikeQueryPort {
    suspend fun findLikedByUser(user: Author, pageRequest: PageRequest): List<ShowcaseCalligraphy>
}