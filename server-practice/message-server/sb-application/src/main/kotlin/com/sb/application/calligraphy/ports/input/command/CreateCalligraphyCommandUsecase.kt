package com.sb.application.calligraphy.ports.input.command

import com.sb.domain.calligraphy.command.CreateCalligraphyCommand
import com.sb.domain.calligraphy.entity.Calligraphy.CalligraphyId


interface CreateCalligraphyCommandUsecase {
    suspend fun create(command: CreateCalligraphyCommand): CalligraphyId
}