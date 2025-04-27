package run.moku.modules.users.application.query.out

import run.moku.modules.users.domain.entity.UserLoginId
import run.moku.modules.users.domain.entity.UserNickname

interface ValidatorUserPort {
    fun checkDuplicateNickname(target: UserNickname)
    fun checkDuplicateLoginId(target: UserLoginId)
}