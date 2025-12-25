package com.sb.domain.calligraphy.policy

import com.sb.domain.calligraphy.exception.CalligraphyErrorCode.AUTHOR_NOT_REGISTERED
import com.sb.domain.calligraphy.exception.CalligraphyErrorCode.INSUFFICIENT_CREDIT
import com.sb.domain.calligraphy.value.Author
import com.sb.domain.calligraphy.value.Author.AuthorRole.GUEST
import com.sb.domain.exception.domainRequire

class CreateCalligraphyPolicy {

    enum class CreditSufficiency {
        SUFFICIENT,
        INSUFFICIENT;

        val isSufficient: Boolean
            get() = this == SUFFICIENT
    }

    companion object {
        const val CREATION_COST = 200

        fun validateCanCreate(author: Author, canAfford: CreditSufficiency) {
            domainRequire(canAfford.isSufficient, INSUFFICIENT_CREDIT) { INSUFFICIENT_CREDIT.message }
            validateRole(author)
        }

        private fun validateRole(author: Author) {
            domainRequire(author.role != GUEST, AUTHOR_NOT_REGISTERED) { AUTHOR_NOT_REGISTERED.message }
        }
    }
}