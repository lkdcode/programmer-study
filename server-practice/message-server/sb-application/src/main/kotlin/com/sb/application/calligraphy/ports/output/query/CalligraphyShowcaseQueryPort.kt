package com.sb.application.calligraphy.ports.output.query

import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphy
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyPage
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphySlice
import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.calligraphy.value.ShowcaseSize
import com.sb.domain.user.entity.User

interface CalligraphyShowcaseQueryPort {
    suspend fun pickRandom(size: ShowcaseSize): List<ShowcaseCalligraphy>
    suspend fun findByUser(user: Author, size: ShowcaseSize): List<ShowcaseCalligraphy>

    suspend fun findShowcaseFeed(
        viewer: User.UserId?,
        pageRequest: PageRequest,
    ): ShowcaseCalligraphyPage

    suspend fun findShowcaseFeed(
        viewer: User.UserId?,
        slicePageRequest: SlicePageRequest,
    ): ShowcaseCalligraphySlice

    suspend fun findShowcaseFeed(
        viewer: User.UserId?,
        cursorPageRequest: CursorPageRequest<Calligraphy.CalligraphyId>,
    ): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>

    suspend fun findByAuthor(
        author: Author,
        viewer: User.UserId?,
        pageRequest: PageRequest,
    ): ShowcaseCalligraphyPage

    suspend fun findByAuthor(
        author: Author,
        viewer: User.UserId?,
        slicePageRequest: SlicePageRequest,
    ): ShowcaseCalligraphySlice

    suspend fun findByAuthor(
        author: Author,
        viewer: User.UserId?,
        cursorPageRequest: CursorPageRequest<Calligraphy.CalligraphyId>,
    ): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>
}