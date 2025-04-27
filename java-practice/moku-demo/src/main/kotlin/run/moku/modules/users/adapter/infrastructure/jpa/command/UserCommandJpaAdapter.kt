package run.moku.modules.users.adapter.infrastructure.jpa.command

import org.springframework.stereotype.Service
import run.moku.modules.users.adapter.infrastructure.jpa.mapper.UserMapper
import run.moku.modules.users.application.command.out.UserCommandPort
import run.moku.modules.users.domain.model.MokuUser
import run.moku.modules.users.domain.model.UserSignUpModel

@Service
class UserCommandJpaAdapter(
    private val repository: UserCommandJpaRepository,
    private val userMapper: UserMapper,
) : UserCommandPort {

    override fun registry(user: UserSignUpModel) = userMapper
        .convert(user)
        .let(repository::save)

    override fun remove(user: MokuUser) {
        println("remove")
    }

    override fun modify(user: MokuUser) {
        println("modify")
    }
}