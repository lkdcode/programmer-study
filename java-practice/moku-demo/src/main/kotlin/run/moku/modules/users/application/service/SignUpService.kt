package run.moku.modules.users.application.service

import org.springframework.stereotype.Service
import run.moku.modules.users.application.usecase.SignUpUsecase
import run.moku.modules.users.domain.model.UserSignUpModel
import run.moku.modules.users.domain.policy.ValidUserPort
import run.moku.modules.users.domain.service.command.UserCommandRepository

@Service
class SignUpService(
    private val validUserPort: ValidUserPort,
    private val userCommandRepository: UserCommandRepository,
) {

    fun invoke(model: UserSignUpModel) = SignUpUsecase(model)
        .checkDuplicateLoginId { validUserPort.checkDuplicateLoginId(it) }
        .checkDuplicateNickname { validUserPort.checkDuplicateNickname(it) }
        .signUp { userCommandRepository.registry(it) }

}