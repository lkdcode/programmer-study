package com.sb.domain.calligraphy.repository

import com.sb.domain.calligraphy.model.ShowcaseCalligraphy
import com.sb.domain.calligraphy.value.ShowcaseSize
import com.sb.domain.calligraphy.value.User

interface CalligraphyShowcaseQueryRepository {
    suspend fun pickRandom(size: ShowcaseSize): List<ShowcaseCalligraphy>
    suspend fun findByUser(user: User, size: ShowcaseSize): List<ShowcaseCalligraphy>
}