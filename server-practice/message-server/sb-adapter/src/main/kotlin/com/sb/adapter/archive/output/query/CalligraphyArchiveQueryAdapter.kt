package com.sb.adapter.archive.output.query

import com.sb.adapter.archive.output.infrastructure.jooq.mapper.sort.toCalligraphyArchiveSortField
import com.sb.adapter.archive.output.infrastructure.r2dbc.mapper.entity.toAggregate
import com.sb.adapter.archive.output.infrastructure.r2dbc.repository.CalligraphyArchiveR2dbcRepository
import com.sb.adapter.like.output.infrastructure.jooq.mapper.vo.*
import com.sb.adapter.user.output.infrastructure.jooq.mapper.vo.toNicknameVo
import com.sb.application.archive.ports.output.query.CalligraphyArchiveQueryPort
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphy
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyCursor
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphyPage
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphySlice
import com.sb.application.common.input.query.page.CursorPageRequest
import com.sb.application.common.input.query.page.PageRequest
import com.sb.application.common.input.query.page.SlicePageRequest
import com.sb.application.common.output.query.page.CursorPageResponse
import com.sb.application.common.output.query.page.PageResponse
import com.sb.application.common.output.query.page.SlicePageResponse
import com.sb.domain.archive.aggregate.CalligraphyArchiveAggregate
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.entity.User
import com.sb.jooq.tables.references.JMST_CALLIGRAPHY
import com.sb.jooq.tables.references.JMST_CALLIGRAPHY_ARCHIVE
import com.sb.jooq.tables.references.JMST_CALLIGRAPHY_LIKE
import com.sb.jooq.tables.references.JMST_USER
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class CalligraphyArchiveQueryAdapter(
    private val repository: CalligraphyArchiveR2dbcRepository,
    private val dsl: DSLContext,
) : CalligraphyArchiveQueryPort {

    override suspend fun existsBy(calligraphyId: Calligraphy.CalligraphyId, user: Author): Boolean =
        repository
            .existsByCalligraphyIdAndUserId(calligraphyId.value, user.userId.value)
            .awaitSingle()

    override suspend fun findByUser(user: Author): List<CalligraphyArchiveAggregate> =
        repository
            .findAllByUserId(user.userId.value)
            .map { it.toAggregate() }
            .collectList()
            .awaitSingle()

    override suspend fun countBy(calligraphyId: Calligraphy.CalligraphyId): Long =
        repository
            .countByCalligraphyId(calligraphyId.value)
            .awaitSingle()

    override suspend fun findArchivedByUser(
        userId: User.UserId,
        pageRequest: PageRequest
    ): ShowcaseCalligraphyPage {
        val query = Flux
            .from(
                dsl
                    .select(
                        JMST_CALLIGRAPHY.ID,
                        JMST_CALLIGRAPHY.SEED,
                        JMST_CALLIGRAPHY.TEXT,
                        JMST_CALLIGRAPHY.PROMPT,
                        JMST_CALLIGRAPHY.STYLE,
                        JMST_CALLIGRAPHY.USER_ID,
                        JMST_CALLIGRAPHY.CREATED_AT,

                        JMST_USER.ID,
                        JMST_USER.NICKNAME,
                        JMST_USER.PROFILE_IMAGE,

                        JMST_CALLIGRAPHY_ARCHIVE.ID,
                        JMST_CALLIGRAPHY_ARCHIVE.CALLIGRAPHY_ID,
                        JMST_CALLIGRAPHY_ARCHIVE.USER_ID,
                        likeCount(),
                    )
                    .from(JMST_CALLIGRAPHY_ARCHIVE)

                    .join(JMST_CALLIGRAPHY)
                    .on(JMST_CALLIGRAPHY.ID.cast(UUID::class.java).eq(JMST_CALLIGRAPHY_ARCHIVE.CALLIGRAPHY_ID))

                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))

                    .where(condition(userId))
                    .orderBy(pageRequest.sort.toCalligraphyArchiveSortField())
                    .limit(pageRequest.pageSize)
                    .offset(pageRequest.offset)
            )
            .map { toShowcaseCalligraphy(it) }
            .collectList()

        val totalItem = Flux
            .from(
                dsl
                    .selectCount()
                    .from(JMST_CALLIGRAPHY_ARCHIVE)

                    .join(JMST_CALLIGRAPHY)
                    .on(JMST_CALLIGRAPHY.ID.cast(UUID::class.java).eq(JMST_CALLIGRAPHY_ARCHIVE.CALLIGRAPHY_ID))

                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))

                    .where(condition(userId))
            )
            .map { it.value1() }
            .next()
            .map {
                PageResponse.of(
                    totalElements = it.toLong(),
                    pageNumber = pageRequest.pageNumber,
                    pageSize = pageRequest.pageSize,
                )
            }

        return Mono
            .zip(query, totalItem)
            .map {
                ShowcaseCalligraphyPage(it.t1, it.t2)
            }
            .awaitSingle()
    }

    override suspend fun findArchivedByUser(
        userId: User.UserId,
        slicePageRequest: SlicePageRequest
    ): ShowcaseCalligraphySlice =
        Flux
            .from(
                dsl
                    .select(
                        JMST_CALLIGRAPHY.ID,
                        JMST_CALLIGRAPHY.SEED,
                        JMST_CALLIGRAPHY.TEXT,
                        JMST_CALLIGRAPHY.PROMPT,
                        JMST_CALLIGRAPHY.STYLE,
                        JMST_CALLIGRAPHY.USER_ID,
                        JMST_CALLIGRAPHY.CREATED_AT,

                        JMST_USER.ID,
                        JMST_USER.NICKNAME,
                        JMST_USER.PROFILE_IMAGE,

                        JMST_CALLIGRAPHY_ARCHIVE.ID,
                        JMST_CALLIGRAPHY_ARCHIVE.CALLIGRAPHY_ID,
                        JMST_CALLIGRAPHY_ARCHIVE.USER_ID,
                        likeCount(),
                    )
                    .from(JMST_CALLIGRAPHY_ARCHIVE)

                    .join(JMST_CALLIGRAPHY)
                    .on(JMST_CALLIGRAPHY.ID.cast(UUID::class.java).eq(JMST_CALLIGRAPHY_ARCHIVE.CALLIGRAPHY_ID))

                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))

                    .where(condition(userId))
                    .orderBy(slicePageRequest.sort.toCalligraphyArchiveSortField())
                    .limit(slicePageRequest.pageSize.plus(1))
                    .offset(slicePageRequest.offset)
            )
            .map { toShowcaseCalligraphy(it) }
            .collectList()
            .map {
                if (it.size > slicePageRequest.pageSize) {
                    ShowcaseCalligraphySlice(
                        it.dropLast(1),
                        SlicePageResponse(
                            pageNumber = slicePageRequest.pageNumber,
                            pageSize = slicePageRequest.pageSize,
                            hasNext = true,
                        )
                    )
                } else {
                    ShowcaseCalligraphySlice(
                        it,
                        SlicePageResponse(
                            pageNumber = slicePageRequest.pageNumber,
                            pageSize = slicePageRequest.pageSize,
                            hasNext = false,
                        )
                    )
                }
            }
            .awaitSingle()

    override suspend fun findArchivedByUser(
        userId: User.UserId,
        cursorPageRequest: CursorPageRequest<Calligraphy.CalligraphyId>
    ): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId> {
        val keyCondition = cursorPageRequest.key?.let {
            JMST_CALLIGRAPHY.ID.cast(UUID::class.java).gt(it.value)
        } ?: DSL.noCondition()

        return Flux.from(
                dsl
                    .select(
                        JMST_CALLIGRAPHY.ID,
                        JMST_CALLIGRAPHY.SEED,
                        JMST_CALLIGRAPHY.TEXT,
                        JMST_CALLIGRAPHY.PROMPT,
                        JMST_CALLIGRAPHY.STYLE,
                        JMST_CALLIGRAPHY.USER_ID,
                        JMST_CALLIGRAPHY.CREATED_AT,

                        JMST_USER.ID,
                        JMST_USER.NICKNAME,
                        JMST_USER.PROFILE_IMAGE,

                        JMST_CALLIGRAPHY_ARCHIVE.ID,
                        JMST_CALLIGRAPHY_ARCHIVE.CALLIGRAPHY_ID,
                        JMST_CALLIGRAPHY_ARCHIVE.USER_ID,
                        likeCount(),
                    )
                    .from(JMST_CALLIGRAPHY_ARCHIVE)

                    .join(JMST_CALLIGRAPHY)
                    .on(JMST_CALLIGRAPHY.ID.cast(UUID::class.java).eq(JMST_CALLIGRAPHY_ARCHIVE.CALLIGRAPHY_ID))

                    .join(JMST_USER)
                    .on(JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID))

                    .where(
                        condition(userId)
                            .and(keyCondition)
                    )
                    .orderBy(cursorPageRequest.sort.toCalligraphyArchiveSortField())
                    .limit(cursorPageRequest.pageSize.plus(1))
            )
            .map { toShowcaseCalligraphy(it) }
            .collectList()
            .map { list ->
                if (list.size > cursorPageRequest.pageSize) {
                    ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>(
                        list.dropLast(1),
                        CursorPageResponse(
                            nextKey = list.last().id,
                        )
                    )
                } else {
                    ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>(
                        list,
                        CursorPageResponse(
                            nextKey = null,
                        )
                    )
                }
            }
            .awaitSingle()
    }

    companion object {
        private const val LIKE_COUNT_ALIAS = "like_count"

        private fun condition(userId: User.UserId) =
            JMST_CALLIGRAPHY_ARCHIVE.USER_ID.eq(userId.value)
                .and(JMST_CALLIGRAPHY_ARCHIVE.IS_DELETED.isFalse)
                .and(JMST_CALLIGRAPHY.IS_DELETED.isFalse)
                .and(JMST_USER.IS_DELETED.isFalse)

        private fun likeCount() =
            DSL
                .field(
                    DSL
                        .selectCount()
                        .from(JMST_CALLIGRAPHY_LIKE)
                        .where(
                            JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID.cast(UUID::class.java)
                                .eq(JMST_CALLIGRAPHY.ID.cast(UUID::class.java))
                                .and(JMST_CALLIGRAPHY_LIKE.IS_DELETED.isFalse)
                        )
                )
                .`as`(LIKE_COUNT_ALIAS)

        private fun toShowcaseCalligraphy(record: org.jooq.Record): ShowcaseCalligraphy {
            val mstCalligraphy = record.into(JMST_CALLIGRAPHY)
            val mstUser = record.into(JMST_USER)
            val likeCount = record.get(LIKE_COUNT_ALIAS, Int::class.java) ?: 0

            return ShowcaseCalligraphy(
                id = mstCalligraphy.toCalligraphyIdVo,
                user = mstCalligraphy.toAuthorVo,
                authorNickname = mstUser.toNicknameVo,
                profileImage = mstUser.profileImage,
                text = mstCalligraphy.toTextVo,
                prompt = mstCalligraphy.toPromptVo,
                style = mstCalligraphy.toStyleVo,
                seed = mstCalligraphy.toSeedVo,
                result = "TODO",
                createdAt = mstCalligraphy.createdAtToInstant,
                isLike = true,
                likeCount = likeCount
            )
        }
    }
}
