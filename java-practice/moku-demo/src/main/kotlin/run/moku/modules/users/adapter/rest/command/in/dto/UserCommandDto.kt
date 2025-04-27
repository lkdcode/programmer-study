package run.moku.modules.users.adapter.rest.command.`in`.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import run.moku.modules.users.domain.entity.UserLoginId
import run.moku.modules.users.domain.entity.UserNickname
import run.moku.modules.users.domain.model.UserSignUpModel
import run.moku.modules.users.domain.value.UserPassword

interface UserCommandDto {
    data class SignUpDTO(
        @field:NotEmpty(message = "로그인 아이디를 입력해주세요.")
        @field:Pattern(regexp = UserLoginId.PATTERN_STRING, message = UserLoginId.PATTERN_MESSAGE)
        val loginId: String,

        @field:NotEmpty(message = "닉네임을 입력해주세요.")
        @field:Size(
            min = UserNickname.MIN_LENGTH,
            max = UserNickname.MAX_LENGTH,
            message = UserNickname.LENGTH_VALID_MESSAGE
        )
        val nickname: String,

        @field:NotEmpty(message = "비밀번호를 입력해주세요.")
        @field:Pattern(regexp = UserPassword.PATTERN_STRING, message = UserPassword.VALID_PATTERN_MESSAGE)
        val password1: String,

        @field:NotEmpty(message = "재확인 비밀번호를 입력해주세요.")
        @field:Pattern(regexp = UserPassword.PATTERN_STRING, message = UserPassword.VALID_PATTERN_MESSAGE)
        val password2: String,
    ) {
        init {
            require(password1 == password2) { "비밀번호와 재확인 비밀번호가 서로 일치하지 않습니다." }
        }

        fun convert() = UserSignUpModel(
            UserLoginId.of(loginId),
            UserPassword.of(password1),
            UserNickname.of(nickname),
        )
    }
}