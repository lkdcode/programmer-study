package com.sb.application.archive.ports.input.command

import com.sb.application.archive.dto.ArchiveCalligraphyCommand
import com.sb.domain.archive.entity.CalligraphyArchive.CalligraphyArchiveId

interface ArchiveCalligraphyCommandUsecase {
    suspend fun archive(command: ArchiveCalligraphyCommand): CalligraphyArchiveId
}