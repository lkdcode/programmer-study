package dev.lkdcode.kafka.domains.adapter.output.kafka.mapper

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import reactor.kafka.sender.SenderResult

fun SenderResult<Void>.convert(): TomatoDto =
    TomatoDto(
        offset = this.recordMetadata().offset(),
        partition = this.recordMetadata().partition(),
    )