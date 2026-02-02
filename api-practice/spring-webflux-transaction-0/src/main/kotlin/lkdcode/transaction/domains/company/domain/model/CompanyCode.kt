package lkdcode.transaction.domains.company.domain.model

import java.util.*

@JvmInline
value class CompanyCode private constructor(val value: String) {
    companion object {
        fun init(): CompanyCode = CompanyCode(UUID.randomUUID().toString().take(8).uppercase())

        fun of(value: String): CompanyCode = CompanyCode(value)
    }
}