package lkdcode.transaction.domains.company.domain.model

@JvmInline
value class Password private constructor(val value: String) {
    companion object {
        val PATTERN: Regex =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")

        fun of(raw: String): Password {
            require(raw.matches(PATTERN)) {
                "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
            }
            return Password(raw)
        }
    }
}