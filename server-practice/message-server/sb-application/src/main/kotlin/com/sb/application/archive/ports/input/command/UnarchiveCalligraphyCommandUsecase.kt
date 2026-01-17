package com.sb.application.archive.ports.input.command

import com.sb.application.archive.dto.UnarchiveCalligraphyCommand

interface UnarchiveCalligraphyCommandUsecase {
    suspend fun unarchive(command: UnarchiveCalligraphyCommand)
}


