package com.sb.domain.calligraphy.repository

import com.sb.domain.calligraphy.model.ShowcaseCalligraphy
import com.sb.domain.calligraphy.value.User
import com.sb.domain.calligraphy.value.ShowcaseSize

interface CalligraphyShowcaseQueryRepository {
    fun pickRandom(size: ShowcaseSize): List<ShowcaseCalligraphy>
    fun findByUser(user: User, size: ShowcaseSize): List<ShowcaseCalligraphy>
}