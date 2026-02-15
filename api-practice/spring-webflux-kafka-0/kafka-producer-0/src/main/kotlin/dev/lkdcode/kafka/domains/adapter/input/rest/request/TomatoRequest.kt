package dev.lkdcode.kafka.domains.adapter.input.rest.request

import dev.lkdcode.kafka.domains.domain.model.TomatoColor
import dev.lkdcode.kafka.domains.domain.model.TomatoVo

data class TomatoRequest(
    val color: TomatoColor,
    val name: String,
) {
    fun toVO(): TomatoVo = TomatoVo(
        color = color,
        name = name,
    )
}
