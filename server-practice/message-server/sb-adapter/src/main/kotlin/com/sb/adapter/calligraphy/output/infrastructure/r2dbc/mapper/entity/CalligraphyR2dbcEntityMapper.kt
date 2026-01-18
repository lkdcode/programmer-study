package com.sb.adapter.calligraphy.output.infrastructure.r2dbc.mapper.entity

import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.entity.CalligraphyR2dbcEntity
import com.sb.adapter.calligraphy.output.infrastructure.r2dbc.mapper.vo.*
import com.sb.domain.calligraphy.aggregate.CalligraphyAggregate
import com.sb.application.calligraphy.dto.command.CreateCalligraphyCommand
import com.sb.domain.calligraphy.value.Author


fun CalligraphyR2dbcEntity.toAggregate(): CalligraphyAggregate =
    CalligraphyAggregate.create(
        idVo,
        seedVo,
        textVo,
        promptVo,
        style,
        Author(userIdVo),
    )

fun CalligraphyAggregate.toR2dbcEntity(): CalligraphyR2dbcEntity {
    val calligraphy = this.snapshot

    return CalligraphyR2dbcEntity(
        calligraphyId = calligraphy.id.value,
        seed = calligraphy.seed.value,
        text = calligraphy.text.value,
        prompt = calligraphy.prompt?.value,
        style = calligraphy.style,
        userId = calligraphy.author.userId.value,
        path = "",
    )
}

fun CreateCalligraphyCommand.toR2dbcEntity(): CalligraphyR2dbcEntity =
    CalligraphyR2dbcEntity(
        calligraphyId = this.id.value,
        seed = this.seed.value,
        text = this.text.value,
        prompt = this.prompt?.value,
        style = this.style,
        userId = this.author.userId.value,
        path = "",
    )