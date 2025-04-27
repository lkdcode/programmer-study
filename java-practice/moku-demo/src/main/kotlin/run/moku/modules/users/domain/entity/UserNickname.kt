package run.moku.modules.users.domain.entity

@JvmInline
value class UserNickname private constructor(
    private val value: String
) {
    init {
        require(value.isNotEmpty()) { "닉네임은 필수입니다." }
        require(value.length in MIN_LENGTH..MAX_LENGTH) { LENGTH_VALID_MESSAGE }
    }

    fun asString(): String = value

    companion object {
        const val MIN_LENGTH = 4;
        const val MAX_LENGTH = 20;
        const val LENGTH_VALID_MESSAGE = "닉네임은 4~20자만 허용됩니다.";
        fun of(value: String): UserNickname = UserNickname(value)
    }
}