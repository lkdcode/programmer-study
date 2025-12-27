package com.sb.application.calligraphy.ports.output.command

import com.sb.domain.calligraphy.value.Author

interface CalligraphyPaymentPort {
    suspend fun payForCreate(author: Author)
}