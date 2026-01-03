package com.sb.adapter.like.input.web.rest.command

import com.sb.application.like.dto.command.LikeCalligraphyCommand
import com.sb.application.like.dto.command.UnlikeCalligraphyCommand
import com.sb.application.like.ports.input.command.LikeCalligraphyCommandUsecase
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.like.entity.CalligraphyLike
import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.created
import com.sb.framework.api.deleted
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class CalligraphyLikeCommandApi(
    private val likeCalligraphyCommandUsecase: LikeCalligraphyCommandUsecase,
) {

    @PostMapping("/api/calligraphies/like/{calligraphyId}")
    suspend fun like(
        @AuthenticationPrincipal auth: UserAuthentication,
        @PathVariable(name = "calligraphyId") calligraphyId: Long,
    ): ApiResponseEntity<Unit> {
        likeCalligraphyCommandUsecase.like(
            LikeCalligraphyCommand(
                calligraphyId = Calligraphy.CalligraphyId(calligraphyId),
                userId = auth.userIdVo,
            )
        )

        return created<Unit>()
    }

    @DeleteMapping("/api/calligraphies/unlike/{likeId}")
    suspend fun unlike(
        @AuthenticationPrincipal auth: UserAuthentication,
        @PathVariable(name = "likeId") likeId: Long,
    ): ApiResponseEntity<Unit> {
        likeCalligraphyCommandUsecase.unlike(
            UnlikeCalligraphyCommand(
                userId = auth.userIdVo,
                likeId = CalligraphyLike.CalligraphyLikeId(likeId)
            )
        )

        return deleted<Unit>()
    }
}