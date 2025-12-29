package com.sb.adapter.user.output.command

import com.sb.adapter.user.output.infrastructure.r2dbc.mapper.toEntity
import com.sb.adapter.user.output.infrastructure.r2dbc.mapper.userIdVo
import com.sb.adapter.user.output.infrastructure.r2dbc.repository.UserR2dbcRepository
import com.sb.adapter.user.output.infrastructure.r2dbc.repository.loadById
import com.sb.application.user.ports.output.command.UserCommandPort
import com.sb.domain.user.aggregate.UserAggregate
import com.sb.domain.user.entity.User
import com.sb.domain.user.model.NewUser
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserCommandAdapter(
    private val repository: UserR2dbcRepository,
    private val passwordEncoder: PasswordEncoder
) : UserCommandPort {

    override suspend fun save(newUser: NewUser): User.UserId =
        repository
            .save(newUser.toEntity(passwordEncoder))
            .awaitSingle()
            .userIdVo

    override suspend fun save(user: UserAggregate): User.UserId =
        repository
            .save(user.snapshot.toEntity())
            .awaitSingle()
            .userIdVo

    override suspend fun update(user: UserAggregate): User.UserId =
        repository
            .loadById(user.snapshot.id)
            .map { it.update(user.snapshot) }
            .flatMap { repository.save(it) }
            .awaitSingle()
            .userIdVo
}