package com.sb.adapter.calligraphy.input.web.rest.command.request

import com.sb.application.calligraphy.dto.command.CreateCalligraphyCommand
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.*
import com.sb.domain.user.entity.User
import java.util.*

interface CalligraphyCommandRequest {

    data class Create(
        val seed: String,
        val text: String,
        val prompt: String?,
        val style: StyleType,
    ) {
        fun toCommand(userId: User.UserId): CreateCalligraphyCommand =
            CreateCalligraphyCommand(
                id = Calligraphy.CalligraphyId(UUID.randomUUID()),
                seed = Seed.of(seed),
                text = Text.of(text),
                prompt = prompt?.let { Prompt.of(it) },
                style = style,
                author = Author(userId),
            )
    }
}