package run.moku.modules.users.domain.service.command

import run.moku.modules.users.domain.model.MokuUser
import run.moku.modules.users.domain.model.UserSignUpModel

interface UserCommandRepository {
    fun registry(user: UserSignUpModel)
    fun remove(user: MokuUser)
    fun modify(user: MokuUser)
}