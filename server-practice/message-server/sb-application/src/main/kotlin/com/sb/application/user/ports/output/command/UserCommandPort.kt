package com.sb.application.user.ports.output.command

import com.sb.domain.user.aggregate.UserAggregate

interface UserCommandPort {
    suspend fun save(aggregate: UserAggregate): UserAggregate
}