package dev.lkdcode.application.ports.output.query

import dev.lkdcode.domain.value.Author

interface UserQueryPort {
    fun load(id: Long): Author
}