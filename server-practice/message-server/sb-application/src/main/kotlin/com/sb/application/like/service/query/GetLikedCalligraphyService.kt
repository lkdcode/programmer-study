package com.sb.application.like.service.query

import com.sb.application.calligraphy.dto.GetLikedCalligraphyQuery
import com.sb.application.like.ports.input.query.LikeCalligraphyQueryUsecase
import com.sb.application.like.ports.output.query.CalligraphyLikeQueryPort
import com.sb.domain.calligraphy.model.ShowcaseCalligraphy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetLikedCalligraphyService(
    private val queryPort: CalligraphyLikeQueryPort,
) : LikeCalligraphyQueryUsecase {

    override suspend fun fetchMyLikedCalligraphies(query: GetLikedCalligraphyQuery): List<ShowcaseCalligraphy> =
        queryPort.findLikedByUser(query.user, query.pageRequest)
}