package run.moku.modules.users.domain.entity

@JvmInline
value class UserId private constructor(
    private val value: Long
) {
    init {
        require(value > 0) { "유저의 식별자는 음수가 될 수 없습니다." }
    }

    fun get(): Long = value

    companion object {
        fun of(id: Long) = UserId(id)
    }
}