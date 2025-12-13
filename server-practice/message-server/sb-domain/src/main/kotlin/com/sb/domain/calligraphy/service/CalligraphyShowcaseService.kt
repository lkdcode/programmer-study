package com.sb.domain.calligraphy.service

import com.sb.domain.calligraphy.model.ShowcaseCalligraphy
import com.sb.domain.calligraphy.repository.CalligraphyShowcaseQueryRepository
import com.sb.domain.calligraphy.value.ShowcaseSize
import com.sb.domain.calligraphy.value.User

class CalligraphyShowcaseService(
    private val queryRepository: CalligraphyShowcaseQueryRepository
) {
    fun getRandomShowcase(size: ShowcaseSize): List<ShowcaseCalligraphy> =
        queryRepository.pickRandom(size)

    fun getShowcaseByUser(user: User, size: ShowcaseSize): List<ShowcaseCalligraphy> =
        queryRepository.findByUser(user, size)
}