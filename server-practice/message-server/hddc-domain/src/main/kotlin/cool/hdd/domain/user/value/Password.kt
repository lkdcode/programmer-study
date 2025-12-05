package cool.hdd.domain.user.value


@JvmInline
value class Password(
    val value: String
) {

    init {
        require(PATTERN.matches(value)) { INVALID_PATTERN_MESSAGE }
    }

    companion object {
        const val NOT_BLANK_MESSAGE = "비밀번호는 필수입니다."

        const val MIN_LENGTH = 8
        const val INVALID_MIN_LENGTH_MESSAGE = "비밀번호는 최소 ${MIN_LENGTH}글자 이상이어야 합니다."

        const val MAX_LENGTH = 30
        const val INVALID_MAX_LENGTH_MESSAGE = "비밀번호는 최대 ${MAX_LENGTH}글자까지 허용됩니다."

        const val PATTERN_STRING = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_=+.]).{$MIN_LENGTH,$MAX_LENGTH}$"
        const val INVALID_PATTERN_MESSAGE =
            "비밀번호는 영어, 숫자, 특수 문자('!','@','#','$','%','^','&','*','(',')','-','_','=','+','.') 가 필수입니다."
        val PATTERN = Regex(PATTERN_STRING)
    }
}