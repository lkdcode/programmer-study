package com.sb.domain.user.repository

import com.sb.domain.user.aggregate.UserAggregate

interface UserRepository {
    fun save(aggregate: UserAggregate): UserAggregate
}


