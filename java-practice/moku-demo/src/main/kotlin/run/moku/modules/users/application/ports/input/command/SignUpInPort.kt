package run.moku.modules.users.application.ports.input.command

import run.moku.modules.users.domain.model.UserSignUpModel

interface SignUpInPort {
    fun perform(model: UserSignUpModel)
}