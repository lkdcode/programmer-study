package com.sb.adapter.like.output.query

import com.sb.adapter.like.output.infrastructure.jooq.mapper.sort.toCalligraphyLikeSortField
import com.sb.adapter.like.output.infrastructure.jooq.mapper.vo.*
import com.sb.adapter.user.output.infrastructure.jooq.mapper.vo.toNicknameVo
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
import com.sb.application.like.ports.output.query.CalligraphyLikeQueryPort
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.user.entity.User
import com.sb.jooq.tables.references.JMST_CALLIGRAPHY
import com.sb.jooq.tables.references.JMST_CALLIGRAPHY_LIKE
import com.sb.jooq.tables.references.JMST_USER
import kotlinx.coroutines.reactive.awaitSingle
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class CalligraphyLikeQueryAdapter(
    private val dsl: DSLContext
) : CalligraphyLikeQueryPort {

    override suspend fun findLikedByUser(
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

                        JMST_CALLIGRAPHY_LIKE.ID,
                        JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID,
                        JMST_CALLIGRAPHY_LIKE.USER_ID,
                        likeCount()
                    )
                    .from(JMST_CALLIGRAPHY_LIKE)

                    .join(JMST_CALLIGRAPHY)
                    .on(
                        JMST_CALLIGRAPHY.ID.cast(UUID::class.java).eq(JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID.cast(UUID::class.java)),
                    )

                    .join(JMST_USER)
                    .on(
                        JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID),
                    )

                    .where(condition(userId))
                    .orderBy(pageRequest.sort.toCalligraphyLikeSortField())
                    .limit(pageRequest.pageSize)
                    .offset(pageRequest.offset)
            )
            .map {
                val mstCalligraphy = it.into(JMST_CALLIGRAPHY)
                val mstUser = it.into(JMST_USER)
                val likeCount = it.get(LIKE_COUNT_ALIAS, Int::class.java) ?: 0

                ShowcaseCalligraphy(
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
            .collectList()

        val totalItem = Flux
            .from(
                dsl
                    .selectCount()
                    .from(JMST_CALLIGRAPHY_LIKE)

                    .join(JMST_CALLIGRAPHY)
                    .on(
                        JMST_CALLIGRAPHY.ID.cast(UUID::class.java).eq(JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID.cast(UUID::class.java)),
                        JMST_CALLIGRAPHY.IS_DELETED.isFalse
                    )

                    .join(JMST_USER)
                    .on(
                        JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID),
                        JMST_USER.IS_DELETED.isFalse
                    )
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
                val items = it.t1
                val pageResponse = it.t2

                ShowcaseCalligraphyPage(items, pageResponse)
            }
            .awaitSingle()
    }

    override suspend fun findLikedByUser(
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

                        JMST_CALLIGRAPHY_LIKE.ID,
                        JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID,
                        JMST_CALLIGRAPHY_LIKE.USER_ID,
                        likeCount()
                    )
                    .from(JMST_CALLIGRAPHY_LIKE)

                    .join(JMST_CALLIGRAPHY)
                    .on(
                        JMST_CALLIGRAPHY.ID.cast(UUID::class.java).eq(JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID.cast(UUID::class.java)),
                    )

                    .join(JMST_USER)
                    .on(
                        JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID),
                    )

                    .where(condition(userId))
                    .orderBy(slicePageRequest.sort.toCalligraphyLikeSortField())
                    .limit(slicePageRequest.pageSize.plus(1))
                    .offset(slicePageRequest.offset)
            )
            .map {
                val mstCalligraphy = it.into(JMST_CALLIGRAPHY)
                val mstUser = it.into(JMST_USER)
                val likeCount = it.get(LIKE_COUNT_ALIAS, Int::class.java) ?: 0

                ShowcaseCalligraphy(
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

    override suspend fun findLikedByUser(
        userId: User.UserId,
        cursorPageRequest: CursorPageRequest<Calligraphy.CalligraphyId>
    ): ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId> {
        val keyCondition = cursorPageRequest.key?.let {
            JMST_CALLIGRAPHY.ID.cast(UUID::class.java).gt(it.value)
        } ?: DSL.noCondition()

        return Flux
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

                        JMST_CALLIGRAPHY_LIKE.ID,
                        JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID,
                        JMST_CALLIGRAPHY_LIKE.USER_ID,
                        likeCount()
                    )
                    .from(JMST_CALLIGRAPHY_LIKE)

                    .join(JMST_CALLIGRAPHY)
                    .on(
                        JMST_CALLIGRAPHY.ID.cast(UUID::class.java).eq(JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID.cast(UUID::class.java)),
                    )

                    .join(JMST_USER)
                    .on(
                        JMST_USER.ID.eq(JMST_CALLIGRAPHY.USER_ID),
                    )

                    .where(
                        condition(userId)
                            .and(keyCondition)
                    )
                    .orderBy(cursorPageRequest.sort.toCalligraphyLikeSortField())
                    .limit(cursorPageRequest.pageSize.plus(1))
            )
            .map {
                val mstCalligraphy = it.into(JMST_CALLIGRAPHY)
                val mstUser = it.into(JMST_USER)
                val likeCount = it.get(LIKE_COUNT_ALIAS, Int::class.java) ?: 0

                ShowcaseCalligraphy(
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
            .collectList()
            .map {
                if (it.size > cursorPageRequest.pageSize) {
                    ShowcaseCalligraphyCursor<Calligraphy.CalligraphyId>(
                        it.dropLast(1),
                        CursorPageResponse<Calligraphy.CalligraphyId>(
                            nextKey = it.last().id,
                        )
                    )
                } else {
                    ShowcaseCalligraphyCursor(
                        it,
                        CursorPageResponse<Calligraphy.CalligraphyId>(
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
            JMST_CALLIGRAPHY_LIKE.USER_ID.eq(userId.value)
                .and(JMST_CALLIGRAPHY_LIKE.IS_DELETED.isFalse)
                .and(JMST_CALLIGRAPHY.IS_DELETED.isFalse)
                .and(JMST_USER.IS_DELETED.isFalse)

        private fun likeCount() =
            DSL
                .field(
                    DSL
                        .selectCount()
                        .from(
                            JMST_CALLIGRAPHY_LIKE
                        )
                        .where(
                            JMST_CALLIGRAPHY_LIKE.CALLIGRAPHY_ID.cast(UUID::class.java)
                                .eq(JMST_CALLIGRAPHY.ID.cast(UUID::class.java))
                                .and(JMST_CALLIGRAPHY_LIKE.IS_DELETED.isFalse)
                        )
                )
                .`as`(LIKE_COUNT_ALIAS)
    }
}