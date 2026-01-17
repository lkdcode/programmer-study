package com.sb.application.archive.service

import com.sb.application.archive.dto.ArchiveCalligraphyCommand
import com.sb.application.archive.exception.ArchiveErrorCode.ALREADY_ARCHIVED
import com.sb.application.archive.ports.input.command.ArchiveCalligraphyCommandUsecase
import com.sb.application.archive.ports.output.command.CalligraphyArchiveCommandPort
import com.sb.application.archive.ports.output.query.CalligraphyArchiveQueryPort
import com.sb.application.exception.applicationRequire
import com.sb.domain.archive.aggregate.CalligraphyArchiveAggregate
import com.sb.domain.archive.entity.CalligraphyArchive.CalligraphyArchiveId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArchiveCalligraphyService(
    private val commandPort: CalligraphyArchiveCommandPort,
    private val queryPort: CalligraphyArchiveQueryPort,
) : ArchiveCalligraphyCommandUsecase {

    override suspend fun archive(command: ArchiveCalligraphyCommand): CalligraphyArchiveId {
        applicationRequire(queryPort.notExistsBy(command.calligraphyId, command.user), ALREADY_ARCHIVED)
        val aggregate = CalligraphyArchiveAggregate.create(command.calligraphyId, command.user)

        return commandPort.save(aggregate).getArchive.id
    }
}