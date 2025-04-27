package run.moku.modules.users.domain.policy

import run.moku.modules.users.domain.entity.UserLoginId
import run.moku.modules.users.domain.entity.UserNickname

interface ValidUserPort {
    fun checkDuplicateLoginId(loginId: UserLoginId)
    fun checkDuplicateNickname(nickname: UserNickname)
}