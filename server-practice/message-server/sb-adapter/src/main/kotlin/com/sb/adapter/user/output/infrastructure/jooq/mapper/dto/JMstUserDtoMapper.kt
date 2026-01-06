package com.sb.adapter.user.output.infrastructure.jooq.mapper.dto

import com.sb.domain.calligraphy.value.Author
import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Nickname

data class JMstUserDto(
    val userId: Long,
    val nickname: String,
    val profileImage: String?,
) {
    val toUserIdVo: User.UserId
        get() = User.UserId(userId)

    val toAuthorVo: Author
        get() = Author(toUserIdVo)

    val toNicknameVo: Nickname
        get() = Nickname.of(nickname)
}