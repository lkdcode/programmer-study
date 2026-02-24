package dev.lkdcode.kafka.domains.application.dto

data class TomatoDtoList(
    val items: List<TomatoDto>
)

data class TomatoDto(
    val offset: Long,
    val partition: Int,
)