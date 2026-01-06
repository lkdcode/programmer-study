package com.sb.adapter.calligraphy.output.infrastructure.jooq.mapper.dto

import com.sb.adapter.user.output.infrastructure.jooq.mapper.dto.JMstUserDto
import com.sb.application.calligraphy.dto.query.ShowcaseCalligraphy
import com.sb.domain.calligraphy.entity.Calligraphy
import com.sb.domain.calligraphy.value.Prompt
import com.sb.domain.calligraphy.value.Seed
import com.sb.domain.calligraphy.value.StyleType
import com.sb.domain.calligraphy.value.Text
import java.time.Instant

data class JMstCalligraphyDto(
    val calligraphyId: Long,
    val seed: String,
    val text: String,
    val prompt: String?,
    val style: StyleType,
    val author: Long,
    val likeCount: Int,
    val createdAt: Instant,
    val result: String,
) {
    val toCalligraphyIdVo: Calligraphy.CalligraphyId
        get() = Calligraphy.CalligraphyId(calligraphyId)

    val toSeedVo: Seed
        get() = Seed.of(this.seed)

    val toTextVo: Text
        get() = Text.of(this.text)

    val toPromptVo: Prompt?
        get() = this.prompt?.let { Prompt.of(this.prompt) }

    fun toShowcaseCalligraphy(
        isLike: Boolean,
        userDto: JMstUserDto,
    ): ShowcaseCalligraphy = ShowcaseCalligraphy(
        id = toCalligraphyIdVo,
        user = userDto.toAuthorVo,
        authorNickname = userDto.toNicknameVo,
        profileImage = userDto.profileImage,
        text = toTextVo,
        prompt = toPromptVo,
        style = style,
        seed = toSeedVo,
        result = result,
        createdAt = createdAt,
        isLike = isLike,
        likeCount = likeCount,
    )
}