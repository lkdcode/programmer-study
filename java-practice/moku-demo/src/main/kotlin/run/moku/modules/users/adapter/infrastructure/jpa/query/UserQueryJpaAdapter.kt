package run.moku.modules.users.adapter.infrastructure.jpa.query

import org.springframework.stereotype.Service
import run.moku.modules.users.application.query.out.UserQueryPort
import run.moku.modules.users.domain.entity.UserId
import run.moku.modules.users.domain.entity.UserLoginId
import run.moku.modules.users.domain.entity.UserNickname
import run.moku.modules.users.domain.model.MokuUser
import run.moku.modules.users.domain.value.UserPassword

@Service
class UserQueryJpaAdapter : UserQueryPort {
    override fun load(): MokuUser {
        println("load")
        return MokuUser(
            UserId.of(1L),
            UserLoginId.of("hi"),
            UserNickname.of("hi"),
            UserPassword.of("hi")
        )
    }
}