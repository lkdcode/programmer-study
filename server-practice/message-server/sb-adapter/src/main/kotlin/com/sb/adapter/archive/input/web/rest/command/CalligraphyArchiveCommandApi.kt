package com.sb.adapter.archive.input.web.rest.command

import com.sb.application.archive.dto.ArchiveCalligraphyCommand
import com.sb.application.archive.dto.UnarchiveCalligraphyCommand
import com.sb.application.archive.ports.input.command.ArchiveCalligraphyCommandUsecase
import com.sb.application.archive.ports.input.command.UnarchiveCalligraphyCommandUsecase
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.created
import com.sb.framework.api.deleted
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class CalligraphyArchiveCommandApi(
    private val archiveCalligraphyCommandUsecase: ArchiveCalligraphyCommandUsecase,
    private val unarchiveCalligraphyCommandUsecase: UnarchiveCalligraphyCommandUsecase,
) {

    @PostMapping("/api/calligraphies/archive/{calligraphyId}")
    suspend fun archive(
        @AuthenticationPrincipal auth: UserAuthentication,
        @PathVariable(name = "calligraphyId") calligraphyId: UUID,
    ): ApiResponseEntity<Long> {
        val archiveId = archiveCalligraphyCommandUsecase.archive(
            ArchiveCalligraphyCommand(
                calligraphyId = Calligraphy.CalligraphyId(calligraphyId),
                user = auth.authorVo,
            )
        )

        return created(archiveId.value)
    }

    @DeleteMapping("/api/calligraphies/archive/{calligraphyId}")
    suspend fun unarchive(
        @AuthenticationPrincipal auth: UserAuthentication,
        @PathVariable(name = "calligraphyId") calligraphyId: UUID,
    ): ApiResponseEntity<Unit> {
        unarchiveCalligraphyCommandUsecase.unarchive(
            UnarchiveCalligraphyCommand(
                calligraphyId = Calligraphy.CalligraphyId(calligraphyId),
                user = auth.authorVo,
            )
        )

        return deleted<Unit>()
    }
}