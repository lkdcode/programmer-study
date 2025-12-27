package com.sb.application.calligraphy.ports.input.command

import com.sb.domain.calligraphy.command.CreateCalligraphyCommand
import com.sb.domain.calligraphy.entity.Calligraphy.CalligraphyId


interface CreateCalligraphyUsecase {
    suspend fun create(command: CreateCalligraphyCommand): CalligraphyId
}