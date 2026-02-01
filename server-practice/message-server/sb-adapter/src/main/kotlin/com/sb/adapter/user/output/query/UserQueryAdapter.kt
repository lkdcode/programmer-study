package com.sb.adapter.user.output.query

import com.sb.adapter.user.output.infrastructure.r2dbc.entity.UserR2dbcEntity
import com.sb.adapter.user.output.infrastructure.r2dbc.mapper.toAggregate
import com.sb.adapter.user.output.infrastructure.r2dbc.repository.UserR2dbcRepository
import com.sb.adapter.user.output.infrastructure.r2dbc.repository.loadByEmail
import com.sb.adapter.user.output.infrastructure.r2dbc.repository.loadById
import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.aggregate.UserAggregate
import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class UserQueryAdapter(
    private val repository: UserR2dbcRepository,
) : UserQueryPort {

    override suspend fun loadById(id: User.UserId): UserAggregate =
        repository
            .loadById(id.value)
            .map(UserR2dbcEntity::toAggregate)
            .awaitSingle()

    override suspend fun loadByEmail(email: Email): UserAggregate =
        repository
            .loadByEmail(email.value)
            .map(UserR2dbcEntity::toAggregate)
            .awaitSingle()

    override suspend fun existsByEmail(email: Email): Boolean =
        repository
            .existsByEmail(email.value)
            .awaitSingle()

    override suspend fun alreadyRegisterByEmail(email: Email): Boolean =
        !existsByEmail(email)
}