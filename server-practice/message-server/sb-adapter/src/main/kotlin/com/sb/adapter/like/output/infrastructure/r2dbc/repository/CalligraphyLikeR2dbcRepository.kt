package com.sb.adapter.like.output.infrastructure.r2dbc.repository

import com.sb.adapter.like.output.infrastructure.r2dbc.entity.CalligraphyLikeR2dbcEntity
import com.sb.domain.like.entity.CalligraphyLike
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.query.Param
import reactor.core.publisher.Mono

interface CalligraphyLikeR2dbcRepository : R2dbcRepository<CalligraphyLikeR2dbcEntity, Long> {

    @Query(
        """
SELECT EXISTS (
    SELECT 1
      FROM mst_calligraphy_like
     WHERE calligraphy_id = :calligraphyId
       AND user_id = :userId
)
"""
    )
    fun existsByCalligraphyIdAndUserId(
        @Param("calligraphyId") calligraphyId: Long,
        @Param("userId") userId: Long,
    ): Mono<Boolean>

    @Query(
        """
SELECT COUNT(*)
  FROM mst_calligraphy_like
 WHERE calligraphy_id = :calligraphyId
"""
    )
    fun countByCalligraphyId(@Param("calligraphyId") calligraphyId: Long): Mono<Long>
}

fun CalligraphyLikeR2dbcRepository.removeById(id: CalligraphyLike.CalligraphyLikeId): Mono<Void> =
    deleteById(id.value)

fun CalligraphyLikeR2dbcRepository.loadById(id: CalligraphyLike.CalligraphyLikeId): Mono<CalligraphyLikeR2dbcEntity> =
    findById(id.value).switchIfEmpty(Mono.error(IllegalArgumentException("TODO loadById")))