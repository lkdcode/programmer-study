package com.sb.application.calligraphy.ports.input.command

import com.sb.application.calligraphy.dto.command.CreateCalligraphyCommand
import com.sb.domain.calligraphy.entity.Calligraphy.CalligraphyId


interface CreateCalligraphyUsecase {
    suspend fun create(command: CreateCalligraphyCommand): CalligraphyId
}