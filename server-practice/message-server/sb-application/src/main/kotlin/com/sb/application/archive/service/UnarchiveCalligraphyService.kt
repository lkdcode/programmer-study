package com.sb.application.archive.service

import com.sb.application.archive.dto.UnarchiveCalligraphyCommand
import com.sb.application.archive.exception.ArchiveErrorCode.NOT_ARCHIVED
import com.sb.application.archive.ports.input.command.UnarchiveCalligraphyCommandUsecase
import com.sb.application.archive.ports.output.command.CalligraphyArchiveCommandPort
import com.sb.application.archive.ports.output.query.CalligraphyArchiveQueryPort
import com.sb.application.exception.applicationRequire
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UnarchiveCalligraphyService(
    private val commandPort: CalligraphyArchiveCommandPort,
    private val queryPort: CalligraphyArchiveQueryPort,
) : UnarchiveCalligraphyCommandUsecase {

    override suspend fun unarchive(command: UnarchiveCalligraphyCommand) {
        applicationRequire(queryPort.existsBy(command.calligraphyId, command.user), NOT_ARCHIVED)

        commandPort.deleteBy(command.calligraphyId, command.user)
    }
}