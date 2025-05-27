package run.moku.modules.users.adapter.out.validator

import org.springframework.stereotype.Service
import run.moku.framework.adapter.validator.throwIf
import run.moku.framework.api.response.ApiResponseCode
import run.moku.modules.users.application.ports.out.query.UserQueryPort
import run.moku.modules.users.application.ports.out.validator.UserValidatorPort
import run.moku.modules.users.domain.entity.UserLoginId
import run.moku.modules.users.domain.entity.UserNickname

@Service
class UserValidatorAdapter(
    val queryUserOutPort: UserQueryPort
) : UserValidatorPort {

    override fun checkDuplicateLoginId(target: UserLoginId) {
        throwIf(queryUserOutPort.existsLoginId(target), ApiResponseCode.DUPLICATE_LOGIN_ID)
    }

    override fun checkDuplicateNickname(target: UserNickname) {
        throwIf(queryUserOutPort.existsNickname(target), ApiResponseCode.DUPLICATE_NICKNAME)
    }
}