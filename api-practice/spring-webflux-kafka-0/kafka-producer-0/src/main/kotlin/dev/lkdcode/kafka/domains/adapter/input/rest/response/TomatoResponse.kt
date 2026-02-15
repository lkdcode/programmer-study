package dev.lkdcode.kafka.domains.adapter.input.rest.response

data class TomatoResponse(
    val offset: Long,
    val partition: Int,
)