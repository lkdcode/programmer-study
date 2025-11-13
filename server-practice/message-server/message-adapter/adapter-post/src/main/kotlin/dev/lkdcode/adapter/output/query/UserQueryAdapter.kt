package dev.lkdcode.adapter.output.query

import dev.lkdcode.application.ports.output.query.UserQueryPort
import dev.lkdcode.domain.value.Author
import org.springframework.stereotype.Service


@Service
class UserQueryAdapter : UserQueryPort {

    override fun load(id: Long): Author =
        Author(
            userId = id,
            username = "username",
            role = Author.UserRole.MODERATOR,
        )
}