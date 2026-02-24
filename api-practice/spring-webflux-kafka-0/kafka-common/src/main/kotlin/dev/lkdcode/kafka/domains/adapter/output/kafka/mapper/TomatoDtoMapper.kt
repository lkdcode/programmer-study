package dev.lkdcode.kafka.domains.adapter.output.kafka.mapper

import dev.lkdcode.kafka.domains.application.dto.TomatoDto
import dev.lkdcode.kafka.domains.application.dto.TomatoDtoList
import reactor.kafka.sender.SenderResult

fun List<SenderResult<Void>>.convert(): TomatoDtoList =
    TomatoDtoList(this.map { it.convert() }.toList())

fun SenderResult<Void>.convert(): TomatoDto =
    TomatoDto(
        offset = this.recordMetadata().offset(),
        partition = this.recordMetadata().partition(),
    )