package run.moku.modules.users.domain.service.query

import run.moku.modules.users.domain.model.MokuUser

interface UserQueryRepository {
    fun load(): MokuUser
}