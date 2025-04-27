package run.moku.modules.users.application.usecase

import run.moku.modules.users.domain.entity.UserLoginId
import run.moku.modules.users.domain.entity.UserNickname
import run.moku.modules.users.domain.model.UserSignUpModel

class SignUpUsecase(
    private val userSignUpModel: UserSignUpModel
) {

    fun checkDuplicateLoginId(action: (UserLoginId) -> Unit): SignUpUsecase {
        action(this.userSignUpModel.loginId)
        return this
    }

    fun checkDuplicateNickname(action: (UserNickname) -> Unit): SignUpUsecase {
        action(this.userSignUpModel.nickname)
        return this
    }

    fun signUp(action: (UserSignUpModel) -> Unit) {
        action(this.userSignUpModel)
    }
}