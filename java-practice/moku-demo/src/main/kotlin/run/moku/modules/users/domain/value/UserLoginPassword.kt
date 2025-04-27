package run.moku.modules.users.domain.value

@JvmInline
value class UserLoginPassword private constructor(
    private val value: String
) {
    init {
        require(value.isNotEmpty()) { "로그인 비밀번호를 입력해주세요." }
    }

    fun asString() = value
}