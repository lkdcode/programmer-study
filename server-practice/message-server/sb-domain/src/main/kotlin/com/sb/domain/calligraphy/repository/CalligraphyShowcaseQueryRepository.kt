package com.sb.domain.calligraphy.repository

import com.sb.domain.calligraphy.model.ShowcaseCalligraphy
import com.sb.domain.calligraphy.value.ShowcaseSize
import com.sb.domain.calligraphy.value.Author

interface CalligraphyShowcaseQueryRepository {
    suspend fun pickRandom(size: ShowcaseSize): List<ShowcaseCalligraphy>
    suspend fun findByUser(user: Author, size: ShowcaseSize): List<ShowcaseCalligraphy>
}