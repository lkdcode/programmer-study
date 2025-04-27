package run.moku.modules.users.adapter.infrastructure.jpa.mapper

import org.springframework.stereotype.Service
import run.moku.modules.users.adapter.infrastructure.jpa.entity.UserJpaEntity
import run.moku.modules.users.domain.model.UserSignUpModel

@Service
class UserMapper {
    fun convert(target: UserSignUpModel) = UserJpaEntity(
        loginId = target.loginId.asString(),
        password = target.password.asString(),
        nickname = target.nickname.asString(),
    )
}