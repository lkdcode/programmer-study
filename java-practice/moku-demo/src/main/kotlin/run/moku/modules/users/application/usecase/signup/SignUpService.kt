package run.moku.modules.users.application.usecase.signup

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import run.moku.modules.users.application.ports.input.command.SignUpInPort
import run.moku.modules.users.application.ports.out.command.UserCommandPort
import run.moku.modules.users.application.ports.out.validator.UserValidatorPort
import run.moku.modules.users.application.usecase.signup.policy.SignUpUsecase
import run.moku.modules.users.application.usecase.signup.policy.checkDuplicateLoginId
import run.moku.modules.users.application.usecase.signup.policy.checkDuplicateNickname
import run.moku.modules.users.application.usecase.signup.policy.signUp
import run.moku.modules.users.domain.model.UserSignUpModel

@Service
@Transactional
class SignUpService(
    private val validator: UserValidatorPort,
    private val commandPort: UserCommandPort,
) : SignUpInPort {

    override fun perform(model: UserSignUpModel) = SignUpUsecase.execute(model) {
        checkDuplicateLoginId(validator::checkDuplicateLoginId)
        checkDuplicateNickname(validator::checkDuplicateNickname)
        signUp(commandPort::registry)
    }
}