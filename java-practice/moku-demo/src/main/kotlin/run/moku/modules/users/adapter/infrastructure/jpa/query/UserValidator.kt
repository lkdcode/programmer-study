package run.moku.modules.users.adapter.infrastructure.jpa.query

import org.springframework.stereotype.Service
import run.moku.modules.users.domain.entity.UserLoginId
import run.moku.modules.users.domain.entity.UserNickname
import run.moku.modules.users.domain.policy.ValidUserPort

@Service
class UserValidator : ValidUserPort {
    override fun checkDuplicateLoginId(loginId: UserLoginId) {
        println("hi")
    }

    override fun checkDuplicateNickname(nickname: UserNickname) {
        println("hi")
    }
}