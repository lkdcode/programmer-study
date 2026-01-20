package com.sb.adapter.calligraphy.output.query

import com.sb.adapter.calligraphy.output.infrastructure.jooq.mapper.sort.toCalligraphySortField
import com.sb.adapter.calligraphy.output.infrastructure.jooq.mapper.vo.*
import com.sb.adapter.user.output.infrastructure.jooq.mapper.vo.toNicknameVo
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphy
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyPage
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphySlice
import com.sb.application.calligraphy.ports.output.query.CalligraphyShowcaseQueryPort
import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.application.common.output.query.page.CursorPageResponse
import com.sb.application.common.output.query.page.PageResponse
import com.sb.application.common.output.query.page.SlicePageResponse
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.calligraphy.value.ShowcaseSize
import com.sb.domain.user.entity.User
import com.sb.jooq.tables.records.JMstCalligraphyRecord
import com.sb.jooq.tables.records.JMstUserRecord
import com.sb.jooq.tables.references.JMST_CALLIGRAPHY
import com.sb.jooq.tables.references.JMST_CALLIGRAPHY_LIKE
import com.sb.jooq.tables.references.JMST_USER
import kotlinx.coroutines.reactive.awaitSingle
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class CalligraphyShowcaseQueryAdapter(
    private val dsl: DSLContext
) : CalligraphyShowcaseQueryPort {
    override suspend fun pickRandom(size: ShowcaseSize): List<ShowcaseCalligraphy> =
        Flux
            .from(
                dsl
                    .select(calligraphyFields() + userFields() + likeCount())
                    .from(JMST_CALLIGRAPHY)
                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))
                    .where(baseCondition())
                    .orderBy(DSL.rand())
                    .limit(size.value)
            )
            .map { mapToShowcaseCalligraphy(it, isLike = false) }
            .collectList()
            .awaitSingle()

    override suspend fun findByUser(
        user: Author,
        size: ShowcaseSize,
    ): List<ShowcaseCalligraphy> =
        Flux
            .from(
                dsl
                    .select(calligraphyFields() + userFields() + likeCount())
                    .from(JMST_CALLIGRAPHY)
                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))
                    .where(baseCondition().and(authorCondition(user)))
                    .orderBy(JMST_CALLIGRAPHY.CREATED_AT.desc())
                    .limit(size.value)
            )
            .map { mapToShowcaseCalligraphy(it, isLike = false) }
            .collectList()
            .awaitSingle()

    override suspend fun findShowcaseFeed(
        viewer: User.UserId?,
        pageRequest: PageRequest,
    ): ShowcaseCalligraphyPage {
        val query = Flux
            .from(
                dsl
                    .select(calligraphyFields() + userFields() + likeCount() + isLikeField(viewer))
                    .from(JMST_CALLIGRAPHY)
                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))
                    .where(baseCondition())
                    .orderBy(pageRequest.sort.toCalligraphySortField())
                    .limit(pageRequest.pageSize)
                    .offset(pageRequest.offset)
            )
            .map { mapToShowcaseCalligraphyWithIsLike(it) }
            .collectList()

        val totalItem = countQuery(baseCondition())

        return Mono
            .zip(query, totalItem)
            .map {
                ShowcaseCalligraphyPage(
                    it.t1,
                    PageResponse.of(
                        totalElements = it.t2,
                        pageNumber = pageRequest.pageNumber,
                        pageSize = pageRequest.pageSize,
                    )
                )
            }
            .awaitSingle()
    }

    override suspend fun findShowcaseFeed(
        viewer: User.UserId?,
        slicePageRequest: SlicePageRequest,
    ): ShowcaseCalligraphySlice =
        Flux
            .from(
                dsl
                    .select(calligraphyFields() + userFields() + likeCount() + isLikeField(viewer))
                    .from(JMST_CALLIGRAPHY)
                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))
                    .where(baseCondition())
                    .orderBy(slicePageRequest.sort.toCalligraphySortField())
                    .limit(slicePageRequest.pageSize + 1)
                    .offset(slicePageRequest.offset)
            )
            .map { mapToShowcaseCalligraphyWithIsLike(it) }
            .collectList()
            .map { items ->
                val hasNext = items.size > slicePageRequest.pageSize
                ShowcaseCalligraphySlice(
                    items = if (hasNext) items.dropLast(1) else items,
                    slicePageResponse = SlicePageResponse(
                        pageNumber = slicePageRequest.pageNumber,
                        pageSize = slicePageRequest.pageSize,
                        hasNext = hasNext,
                    )
                )
            }
            .awaitSingle()

    override suspend fun findShowcaseFeed(
        viewer: User.UserId?,
        cursorPageRequest: CursorPageRequest<Calligraphy.CalligraphyId>,
    ): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId> {
        val cursorCondition = cursorPageRequest.key?.let {
            JMST_CALLIGRAPHY.ID.lt(it.value)
        } ?: DSL.noCondition()

        return Flux
            .from(
                dsl
                    .select(calligraphyFields() + userFields() + likeCount() + isLikeField(viewer))
                    .from(JMST_CALLIGRAPHY)
                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))
                    .where(baseCondition().and(cursorCondition))
                    .orderBy(cursorPageRequest.sort.toCalligraphySortField())
                    .limit(cursorPageRequest.pageSize + 1)
            )
            .map { mapToShowcaseCalligraphyWithIsLike(it) }
            .collectList()
            .map { items ->
                val hasNext = items.size > cursorPageRequest.pageSize
                val resultItems = if (hasNext) items.dropLast(1) else items
                ShowcaseCalligraphyCursor(
                    items = resultItems,
                    cursorPageResponse = CursorPageResponse(
                        nextKey = if (hasNext) resultItems.lastOrNull()?.id else null,
                    )
                )
            }
            .awaitSingle()
    }

    override suspend fun findByAuthor(
        author: Author,
        viewer: User.UserId?,
        pageRequest: PageRequest,
    ): ShowcaseCalligraphyPage {
        val condition = baseCondition().and(authorCondition(author))

        val query = Flux
            .from(
                dsl
                    .select(calligraphyFields() + userFields() + likeCount() + isLikeField(viewer))
                    .from(JMST_CALLIGRAPHY)
                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))
                    .where(condition)
                    .orderBy(pageRequest.sort.toCalligraphySortField())
                    .limit(pageRequest.pageSize)
                    .offset(pageRequest.offset)
            )
            .map { mapToShowcaseCalligraphyWithIsLike(it) }
            .collectList()

        val totalItem = countQuery(condition)

        return Mono
            .zip(query, totalItem)
            .map {
                ShowcaseCalligraphyPage(
                    it.t1,
                    PageResponse.of(
                        totalElements = it.t2,
                        pageNumber = pageRequest.pageNumber,
                        pageSize = pageRequest.pageSize,
                    )
                )
            }
            .awaitSingle()
    }

    override suspend fun findByAuthor(
        author: Author,
        viewer: User.UserId?,
        slicePageRequest: SlicePageRequest,
    ): ShowcaseCalligraphySlice =
        Flux
            .from(
                dsl
                    .select(calligraphyFields() + userFields() + likeCount() + isLikeField(viewer))
                    .from(JMST_CALLIGRAPHY)
                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))
                    .where(baseCondition().and(authorCondition(author)))
                    .orderBy(slicePageRequest.sort.toCalligraphySortField())
                    .limit(slicePageRequest.pageSize + 1)
                    .offset(slicePageRequest.offset)
            )
            .map { mapToShowcaseCalligraphyWithIsLike(it) }
            .collectList()
            .map { items ->
                val hasNext = items.size > slicePageRequest.pageSize
                ShowcaseCalligraphySlice(
                    items = if (hasNext) items.dropLast(1) else items,
                    slicePageResponse = SlicePageResponse(
                        pageNumber = slicePageRequest.pageNumber,
                        pageSize = slicePageRequest.pageSize,
                        hasNext = hasNext,
                    )
                )
            }
            .awaitSingle()

    override suspend fun findByAuthor(
        author: Author,
        viewer: User.UserId?,
        cursorPageRequest: CursorPageRequest<Calligraphy.CalligraphyId>,
    ): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId> {
        val cursorCondition = cursorPageRequest.key?.let {
            JMST_CALLIGRAPHY.ID.lt(it.value)
        } ?: DSL.noCondition()

        return Flux
            .from(
                dsl
                    .select(calligraphyFields() + userFields() + likeCount() + isLikeField(viewer))
                    .from(JMST_CALLIGRAPHY)
                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))
                    .where(baseCondition().and(authorCondition(author)).and(cursorCondition))
                    .orderBy(cursorPageRequest.sort.toCalligraphySortField())
                    .limit(cursorPageRequest.pageSize + 1)
            )
            .map { mapToShowcaseCalligraphyWithIsLike(it) }
            .collectList()
            .map { items ->
                val hasNext = items.size > cursorPageRequest.pageSize
                val resultItems = if (hasNext) items.dropLast(1) else items
                ShowcaseCalligraphyCursor(
                    items = resultItems,
                    cursorPageResponse = CursorPageResponse(
                        nextKey = if (hasNext) resultItems.lastOrNull()?.id else null,
                    )
                )
            }
            .awaitSingle()
    }

    private fun calligraphyFields() = listOf(
        JMST_CALLIGRAPHY.ID,
        JMST_CALLIGRAPHY.SEED,
        JMST_CALLIGRAPHY.TEXT,
        JMST_CALLIGRAPHY.PROMPT,
        JMST_CALLIGRAPHY.STYLE,
        JMST_CALLIGRAPHY.USER_ID,
        JMST_CALLIGRAPHY.CREATED_AT,
        JMST_CALLIGRAPHY.PATH,
    )

    private fun userFields() = listOf(
        JMST_USER.ID,
        JMST_USER.NICKNAME,
        JMST_USER.PROFILE_IMAGE,
    )

    private fun baseCondition(): Condition =
        JMST_CALLIGRAPHY.IS_DELETED.isFalse
            .and(JMST_USER.IS_DELETED.isFalse)

    private fun authorCondition(author: Author): Condition =
        JMST_CALLIGRAPHY.USER_ID.eq(author.userId.value)

    private fun likeCount() =
        DSL
            .field(
                DSL
                    .selectCount()
                    .from(JMST_CALLIGRAPHY_LIKE)
                    .where(
                        JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID.eq(JMST_CALLIGRAPHY.ID)
                            .and(JMST_CALLIGRAPHY_LIKE.IS_DELETED.isFalse)
                    )
            )
            .`as`(LIKE_COUNT_ALIAS)

    private fun isLikeField(viewer: User.UserId?) =
        viewer?.let {
            DSL
                .field(
                    DSL.exists(
                        DSL
                            .selectOne()
                            .from(JMST_CALLIGRAPHY_LIKE)
                            .where(
                                JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID.eq(JMST_CALLIGRAPHY.ID)
                                    .and(JMST_CALLIGRAPHY_LIKE.USER_ID.eq(it.value))
                                    .and(JMST_CALLIGRAPHY_LIKE.IS_DELETED.isFalse)
                            )
                    )
                )
                .`as`(IS_LIKE_ALIAS)
        } ?: DSL.inline(false).`as`(IS_LIKE_ALIAS)

    private fun countQuery(condition: Condition): Mono<Long> =
        Flux
            .from(
                dsl
                    .selectCount()
                    .from(JMST_CALLIGRAPHY)
                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))
                    .where(condition)
            )
            .map { it.value1().toLong() }
            .next()

    private fun mapToShowcaseCalligraphy(record: Record, isLike: Boolean): ShowcaseCalligraphy {
        val calligraphy = record.into(JMST_CALLIGRAPHY)
        val user = record.into(JMST_USER)
        val likeCount = record.get(LIKE_COUNT_ALIAS, Int::class.java) ?: 0

        return buildShowcaseCalligraphy(calligraphy, user, likeCount, isLike)
    }

    private fun mapToShowcaseCalligraphyWithIsLike(record: Record): ShowcaseCalligraphy {
        val calligraphy = record.into(JMST_CALLIGRAPHY)
        val user = record.into(JMST_USER)
        val likeCount = record.get(LIKE_COUNT_ALIAS, Int::class.java) ?: 0
        val isLike = record.get(IS_LIKE_ALIAS, Boolean::class.java) ?: false

        return buildShowcaseCalligraphy(calligraphy, user, likeCount, isLike)
    }

    private fun buildShowcaseCalligraphy(
        calligraphy: JMstCalligraphyRecord,
        user: JMstUserRecord,
        likeCount: Int,
        isLike: Boolean,
    ): ShowcaseCalligraphy =
        ShowcaseCalligraphy(
            id = calligraphy.toCalligraphyIdVo,
            user = calligraphy.toAuthorVo,
            authorNickname = user.toNicknameVo,
            profileImage = user.profileImage,
            text = calligraphy.toTextVo,
            prompt = calligraphy.toPromptVo,
            style = calligraphy.toStyleVo,
            seed = calligraphy.toSeedVo,
            result = calligraphy.path,
            createdAt = calligraphy.createdAtToInstant,
            isLike = isLike,
            likeCount = likeCount,
        )

    companion object {
        private const val LIKE_COUNT_ALIAS = "like_count"
        private const val IS_LIKE_ALIAS = "is_like"
    }
}
