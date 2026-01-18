package com.sb.adapter.calligraphy.input.web.rest.command

import com.sb.adapter.calligraphy.input.web.rest.command.request.CalligraphyCommandRequest
import com.sb.application.calligraphy.ports.input.command.CreateCalligraphyUsecase
import com.sb.application.calligraphy.ports.input.command.DeleteCalligraphyUsecase
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.created
import com.sb.framework.api.deleted
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
class CalligraphyCommandApi(
    private val createCalligraphyUsecase: CreateCalligraphyUsecase,
    private val deleteCalligraphyUsecase: DeleteCalligraphyUsecase
) {

    @PostMapping("/api/calligraphies")
    suspend fun create(
        @AuthenticationPrincipal auth: UserAuthentication,
        @RequestBody request: CalligraphyCommandRequest.Create,
    ): ApiResponseEntity<Unit> {
        val command = request.toCommand(auth.userIdVo)
        createCalligraphyUsecase.create(command)

        return created<Unit>()
    }

    @DeleteMapping("/api/calligraphies/{calligraphyId}")
    suspend fun delete(
        @AuthenticationPrincipal auth: UserAuthentication,
        @PathVariable(name = "calligraphyId") calligraphyId: UUID,
    ): ApiResponseEntity<Unit> {
        deleteCalligraphyUsecase.delete(auth.userIdVo, Calligraphy.CalligraphyId(calligraphyId))

        return deleted<Unit>()
    }
}