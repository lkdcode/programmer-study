package run.moku.modules.users.domain.value

class UserPassword private constructor(
    private val value: String
) {
    init {
        require(value.isNotEmpty()) { "비밀번호는 필수입니다." }
        require(PATTERN.matches(value)) { VALID_PATTERN_MESSAGE }
    }

    fun asString(): String = value

    companion object {
        const val PATTERN_STRING = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*])[A-Za-z\\d!@#\$%^&*]{8,20}$";
        const val VALID_PATTERN_MESSAGE = "비밀번호는 영어, 숫자, 특수문자 ['!','@','#','$','%','^','&','*'] 조합과 8~20자리만 허용됩니다."
        private val PATTERN = Regex(PATTERN_STRING)

        fun of(value: String): UserPassword = UserPassword(value)
    }
}